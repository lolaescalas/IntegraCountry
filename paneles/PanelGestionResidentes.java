package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.abstractas.Usuario;
import modelo.espacios.Lote;
import modelo.usuarios.Inquilino;
import modelo.usuarios.Propietario;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelGestionResidentes extends JPanel {

    private final AdministracionFacade fachada;
    private DefaultTableModel modelo;

    public PanelGestionResidentes(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JButton btnNuevo = UI.boton("+ Nuevo Residente", UI.PRIMARIO);
        btnNuevo.addActionListener(e -> nuevoResidente());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UI.titulo("Gestión de Residentes"), BorderLayout.WEST);
        top.add(btnNuevo, BorderLayout.EAST);

        String[] cols = {"Lote", "Nombre", "DNI", "Tipo", "Estado"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        add(top, BorderLayout.NORTH);
        add(UI.tabla(new JTable(modelo)), BorderLayout.CENTER);
        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Usuario u : fachada.getResidentes()) {
            String tipo, lote, estado;
            if (u instanceof Propietario p) {
                tipo = "Propietario"; lote = p.getLote().getEtiqueta(); estado = p.getLote().getEstado();
            } else if (u instanceof Inquilino i) {
                tipo = "Inquilino"; lote = i.getLote().getEtiqueta(); estado = i.getLote().getEstado();
            } else { tipo = "Residente"; lote = "-"; estado = "-"; }
            modelo.addRow(new Object[]{lote, u.getNombre(), u.getDni(), tipo, estado});
        }
    }

    private void nuevoResidente() {
        JTextField nombre = new JTextField();
        JTextField dni = new JTextField();
        JTextField lote = new JTextField();
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Propietario", "Inquilino"});
        Object[] form = {"Nombre:", nombre, "DNI:", dni, "Nro de lote:", lote, "Tipo:", tipo};

        int op = JOptionPane.showConfirmDialog(this, form, "Nuevo Residente", JOptionPane.OK_CANCEL_OPTION);
        if (op != JOptionPane.OK_OPTION) return;
        if (nombre.getText().isBlank() || dni.getText().isBlank() || lote.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int nro;
        try { nro = Integer.parseInt(lote.getText().trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El lote debe ser un número.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si el lote no existe en el barrio, lo creamos y lo sumamos AL BARRIO
        // (asi guardia tambien lo ve, porque ambos leen del mismo barrio).
        Lote l = null;
        for (Lote existente : fachada.getLotes())
            if (existente.getNumero() == nro) { l = existente; break; }
        if (l == null) {
            l = new Lote(nro, "Activo");
            fachada.getBarrio().agregarLote(l);
        }

        int cod = fachada.getResidentes().size() + 1;
        Usuario nuevo = "Propietario".equals(tipo.getSelectedItem())
                ? new Propietario(cod, l, nombre.getText().trim(), dni.getText().trim(), "", "")
                : new Inquilino(cod, l, nombre.getText().trim(), dni.getText().trim(), "", "");
        fachada.registrarResidente(nuevo);
        refrescar();
        JOptionPane.showMessageDialog(this, "Residente registrado correctamente.");
    }
}
