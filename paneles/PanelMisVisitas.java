package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.espacios.Autorizacion;
import patrones.facade.AdministracionFacade;
import vistas.Sesion;
import vistas.UI;

public class PanelMisVisitas extends JPanel {

    private final AdministracionFacade fachada;
    private final Sesion sesion;
    private JTextField txtNombre, txtDni;
    private JComboBox<String> cmbTipo;
    private DefaultTableModel modelo;

    public PanelMisVisitas(AdministracionFacade fachada, Sesion sesion) {
        this.fachada = fachada;
        this.sesion = sesion;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel form = UI.card(new FlowLayout(FlowLayout.LEFT, 12, 8));
        txtNombre = new JTextField(16);
        txtDni = new JTextField(12);
        cmbTipo = new JComboBox<>(new String[]{"Visita", "Proveedor", "Delivery"});
        JButton btn = UI.boton("Autorizar", UI.PRIMARIO);
        btn.addActionListener(e -> autorizar());
        form.add(lbl("Nombre:")); form.add(txtNombre);
        form.add(lbl("DNI:")); form.add(txtDni);
        form.add(lbl("Tipo:")); form.add(cmbTipo);
        form.add(Box.createHorizontalStrut(12)); form.add(btn);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Autorizar Visitas"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        String[] cols = {"Nombre", "DNI", "Tipo", "Estado"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        add(norte, BorderLayout.NORTH);
        add(UI.tabla(new JTable(modelo)), BorderLayout.CENTER);
        refrescar();
    }

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l; }

    private void autorizar() {
        String nom = txtNombre.getText().trim();
        String dni = txtDni.getText().trim();
        if (nom.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete nombre y DNI.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        fachada.autorizarVisita(nom, dni, (String) cmbTipo.getSelectedItem(), sesion.getUsuario());
        txtNombre.setText(""); txtDni.setText("");
        refrescar();
        JOptionPane.showMessageDialog(this, "Visita autorizada. El guardia ya puede dejarla entrar.");
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Autorizacion a : fachada.getAutorizacionesDe(sesion.getUsuario())) {
            modelo.addRow(new Object[]{ a.getNombreVisita(), a.getDniVisita(), a.getTipo(),
                    a.isUsada() ? "Ya ingresó" : "Pendiente de ingreso" });
        }
    }
}
