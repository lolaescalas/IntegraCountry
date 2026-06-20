package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.abstractas.Solicitud;
import modelo.enums.NivelRiesgo;
import modelo.solicitudes.IncidenteSeguridad;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelReporteIncidentes extends JPanel {

    private final AdministracionFacade fachada;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbRiesgo;
    private DefaultTableModel modelo;

    public PanelReporteIncidentes(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel form = UI.card(new BorderLayout(10, 10));
        JLabel info = new JLabel("Describa el incidente para alertar a la administración:");
        info.setFont(UI.BODY);
        txtDescripcion = new JTextArea(5, 40);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(UI.BORDE));

        JPanel ctrl = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        ctrl.setOpaque(false);
        cmbRiesgo = new JComboBox<>(new String[]{"BAJO", "MEDIO", "ALTO", "CRITICO"});
        JButton btn = UI.boton("Emitir Alerta", UI.PELIGRO);
        btn.addActionListener(e -> reportar());
        JLabel lr = new JLabel("Nivel de Riesgo:"); lr.setFont(UI.BOLD);
        ctrl.add(lr); ctrl.add(cmbRiesgo); ctrl.add(Box.createHorizontalStrut(16)); ctrl.add(btn);

        form.add(info, BorderLayout.NORTH);
        form.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);
        form.add(ctrl, BorderLayout.SOUTH);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Reporte de Incidentes de Seguridad"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        String[] cols = {"ID", "Fecha", "Riesgo", "Descripción", "Estado"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        add(norte, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);
        refrescar();
    }

    private void reportar() {
        String desc = txtDescripcion.getText().trim();
        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Describa el incidente.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NivelRiesgo nivel = NivelRiesgo.valueOf((String) cmbRiesgo.getSelectedItem());
        fachada.reportarIncidente(desc, nivel);
        txtDescripcion.setText("");
        refrescar();
        JOptionPane.showMessageDialog(this, "Incidente reportado.", "Alerta Emitida", JOptionPane.WARNING_MESSAGE);
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Solicitud s : fachada.getSolicitudes()) {
            if (s instanceof IncidenteSeguridad i) {
                modelo.addRow(new Object[]{
                        "INC-" + i.getId(), i.getFecha(), i.getNivelRiesgo(),
                        i.getDescripcion(), i.getNombreEstado()
                });
            }
        }
    }
}
