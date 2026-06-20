package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.abstractas.Solicitud;
import modelo.enums.Prioridad;
import modelo.solicitudes.Reclamo;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelMisReclamos extends JPanel {

    private final AdministracionFacade fachada;
    private JTextField txtAsunto;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbPrioridad;
    private DefaultTableModel modelo;

    public PanelMisReclamos(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        // ----- Formulario (tarjeta) -----
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

        g.gridx = 0; g.gridy = 0; form.add(etiqueta("Asunto:"), g);
        g.gridx = 1; form.add(txtAsunto, g);
        g.gridx = 2; form.add(etiqueta("Prioridad:"), g);
        g.gridx = 3; form.add(cmbPrioridad, g);

        g.gridx = 0; g.gridy = 1; g.anchor = GridBagConstraints.NORTHWEST;
        form.add(etiqueta("Descripción:"), g);
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

        // ----- Tabla -----
        String[] cols = {"ID", "Asunto", "Fecha", "Prioridad", "Estado Actual"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        add(norte, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);

        refrescar();
    }

    private JLabel etiqueta(String t) {
        JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l;
    }

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
        fachada.gestionarReclamo(asunto, desc, p);   // crea, notifica (Observer) y guarda en repo
        txtAsunto.setText("");
        txtDescripcion.setText("");
        cmbPrioridad.setSelectedIndex(0);
        refrescar();
        JOptionPane.showMessageDialog(this, "Reclamo registrado correctamente.");
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Solicitud s : fachada.getSolicitudes()) {
            if (s instanceof Reclamo r) {
                modelo.addRow(new Object[]{
                        "REQ-" + r.getId(), r.getAsunto(), r.getFecha(),
                        r.getPrioridad(), r.getNombreEstado()
                });
            }
        }
    }
}
