package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.abstractas.Usuario;
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

        String[] cols = {"Lote", "Nombre", "DNI", "Usuario", "Tipo"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        add(top, BorderLayout.NORTH);
        add(UI.tabla(new JTable(modelo)), BorderLayout.CENTER);
        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Usuario u : fachada.getResidentes()) {
            String tipo, lote;
            if (u instanceof Propietario p) { tipo = "Propietario"; lote = p.getLote().getEtiqueta(); }
            else if (u instanceof Inquilino i) { tipo = "Inquilino"; lote = i.getLote().getEtiqueta(); }
            else { tipo = "Residente"; lote = "-"; }
            modelo.addRow(new Object[]{lote, u.getNombre(), u.getDni(), u.getUsername(), tipo});
        }
    }

    private void nuevoResidente() {
        JTextField nombre = new JTextField();
        JTextField dni = new JTextField();
        JTextField lote = new JTextField();
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        Object[] form = {"Nombre:", nombre, "DNI:", dni, "Nro de lote:", lote,
                "Usuario:", username, "Contraseña:", password};

        int op = JOptionPane.showConfirmDialog(this, form, "Nuevo Residente", JOptionPane.OK_CANCEL_OPTION);
        if (op != JOptionPane.OK_OPTION) return;

        String nom = nombre.getText().trim();
        String doc = dni.getText().trim();
        String user = username.getText().trim();
        String pass = new String(password.getPassword());
        if (nom.isEmpty() || doc.isEmpty() || lote.getText().isBlank() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int nro;
        try { nro = Integer.parseInt(lote.getText().trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El lote debe ser un número.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fachada.existeUsername(user)) {
            JOptionPane.showMessageDialog(this, "Ese usuario ya existe.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        fachada.registrarResidente(nom, doc, nro, user, pass);
        refrescar();
        JOptionPane.showMessageDialog(this, "Residente registrado correctamente.");
    }
}
