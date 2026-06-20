package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelMisReservas extends JPanel {

    private final AdministracionFacade fachada;
    private JComboBox<String> cmbEspacio, cmbHora;
    private JTextField txtFecha;
    private DefaultTableModel modelo;

    public PanelMisReservas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel form = UI.card(new FlowLayout(FlowLayout.LEFT, 12, 8));
        cmbEspacio = new JComboBox<>(new String[]{"S.U.M.", "Cancha de Tenis", "Cancha de Fútbol", "Parrilla 1"});
        txtFecha = new JTextField(10);
        cmbHora = new JComboBox<>(new String[]{"10:00", "12:00", "14:00", "16:00", "18:00", "20:00"});
        JButton btn = UI.boton("Solicitar Reserva", UI.PRIMARIO);
        btn.addActionListener(e -> reservar());

        form.add(lbl("Espacio:")); form.add(cmbEspacio);
        form.add(lbl("Fecha (DD/MM/AAAA):")); form.add(txtFecha);
        form.add(lbl("Hora:")); form.add(cmbHora);
        form.add(Box.createHorizontalStrut(12)); form.add(btn);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Mis Reservas"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        String[] cols = {"Espacio", "Fecha", "Hora", "Estado"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        add(norte, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);
    }

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l; }

    private void reservar() {
        if (txtFecha.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Indique la fecha.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        fachada.solicitarReserva((String) cmbEspacio.getSelectedItem(), txtFecha.getText().trim(), (String) cmbHora.getSelectedItem());
        modelo.addRow(new Object[]{cmbEspacio.getSelectedItem(), txtFecha.getText().trim(), cmbHora.getSelectedItem(), "Pendiente"});
        txtFecha.setText("");
    }
}
