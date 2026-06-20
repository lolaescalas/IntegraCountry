package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.abstractas.Solicitud;
import modelo.enums.Prioridad;
import modelo.solicitudes.Reclamo;
import patrones.facade.AdministracionFacade;
import vistas.Sesion;
import vistas.UI;

public class PanelMisReclamos extends JPanel {

    private final AdministracionFacade fachada;
    private final Sesion sesion;
    private JTextField txtAsunto;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbPrioridad;
    private DefaultTableModel modelo;

    public PanelMisReclamos(AdministracionFacade fachada, Sesion sesion) {
        this.fachada = fachada;
        this.sesion = sesion;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel form = UI.card(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        txtAsunto = new JTextField(22);
        cmbPrioridad = new JComboBox<>(new String[]{"Baja", "Media", "Alta"});
        txtDescripcion = new JTextArea(4, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(UI.BORDE));

        g.gridx = 0; g.gridy = 0; form.add(lbl("Asunto:"), g);
        g.gridx = 1; form.add(txtAsunto, g);
        g.gridx = 2; form.add(lbl("Prioridad:"), g);
        g.gridx = 3; form.add(cmbPrioridad, g);
        g.gridx = 0; g.gridy = 1; g.anchor = GridBagConstraints.NORTHWEST;
        form.add(lbl("Descripción:"), g);
        g.gridx = 1; g.gridwidth = 3; g.fill = GridBagConstraints.BOTH;
        form.add(new JScrollPane(txtDescripcion), g);

        JButton btnEnviar = UI.boton("Enviar Reclamo", UI.PRIMARIO);
        btnEnviar.addActionListener(e -> enviar());
        g.gridx = 3; g.gridy = 2; g.gridwidth = 1; g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.EAST;
        form.add(btnEnviar, g);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Mis Reclamos y Solicitudes"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        String[] cols = {"ID", "Asunto", "Fecha", "Prioridad", "Estado Actual"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        add(norte, BorderLayout.NORTH);
        add(UI.tabla(new JTable(modelo)), BorderLayout.CENTER);
        refrescar();
    }

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l; }

    private void enviar() {
        String asunto = txtAsunto.getText().trim();
        String desc = txtDescripcion.getText().trim();
        if (asunto.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete asunto y descripción.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Prioridad p = switch ((String) cmbPrioridad.getSelectedItem()) {
            case "Media" -> Prioridad.MEDIA;
            case "Alta"  -> Prioridad.ALTA;
            default       -> Prioridad.BAJA;
        };
        fachada.gestionarReclamo(asunto, desc, p, sesion.getUsuario());
        txtAsunto.setText(""); txtDescripcion.setText(""); cmbPrioridad.setSelectedIndex(0);
        refrescar();
        JOptionPane.showMessageDialog(this, "Reclamo registrado correctamente.");
    }

    // Solo muestra los reclamos del residente logueado
    private void refrescar() {
        modelo.setRowCount(0);
        for (Solicitud s : fachada.getSolicitudes()) {
            if (s instanceof Reclamo r && r.getSolicitante() == sesion.getUsuario()) {
                modelo.addRow(new Object[]{ "REQ-" + r.getId(), r.getAsunto(), r.getFecha(), r.getPrioridad(), r.getNombreEstado() });
            }
        }
    }
}
