package paneles;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.enums.Prioridad;
import patrones.facade.AdministracionFacade;

public class PanelMisReclamos extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(79, 70, 229);
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);

    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbPrioridad;
    
    private AdministracionFacade fachada;

    public PanelMisReclamos(AdministracionFacade fachada) {
        this.fachada = fachada;
        
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Mis Reclamos y Solicitudes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JPanel panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JPanel panelFila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelFila1.setOpaque(false);
        
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        JLabel lblAsunto = new JLabel("Asunto/Título:"); lblAsunto.setFont(fontLabel);
        txtTitulo = new JTextField(25);
        
        JLabel lblPrioridad = new JLabel("Prioridad:"); lblPrioridad.setFont(fontLabel);
        cmbPrioridad = new JComboBox<>(new String[]{"Baja", "Media", "Alta"});
        
        panelFila1.add(lblAsunto); panelFila1.add(txtTitulo);
        panelFila1.add(Box.createHorizontalStrut(20));
        panelFila1.add(lblPrioridad); panelFila1.add(cmbPrioridad);

        JPanel panelFila2 = new JPanel(new BorderLayout(5, 5));
        panelFila2.setOpaque(false);
        JLabel lblDesc = new JLabel("Descripción del problema:"); lblDesc.setFont(fontLabel);
        txtDescripcion = new JTextArea(4, 40);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelFila2.add(lblDesc, BorderLayout.NORTH);
        panelFila2.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

        JButton btnEnviar = new JButton("Enviar Reclamo");
        estilizarBotonPrimario(btnEnviar);

        btnEnviar.addActionListener(e -> {
            String asunto = txtTitulo.getText();
            String descripcion = txtDescripcion.getText();
            String seleccionPrioridad = (String) cmbPrioridad.getSelectedItem();
            
            Prioridad prioridadEnum = Prioridad.BAJA;
            if ("Media".equals(seleccionPrioridad)) prioridadEnum = Prioridad.MEDIA;
            if ("Alta".equals(seleccionPrioridad)) prioridadEnum = Prioridad.ALTA;

            if (asunto.trim().isEmpty() || descripcion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete el asunto y la descripción.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                this.fachada.gestionarReclamo(asunto, descripcion, prioridadEnum);
                
                JOptionPane.showMessageDialog(this, "Reclamo registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                txtTitulo.setText(""); 
                txtDescripcion.setText("");
                cmbPrioridad.setSelectedIndex(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar el reclamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnEnviar);

        panelFormulario.add(panelFila1, BorderLayout.NORTH);
        panelFormulario.add(panelFila2, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);

        String[] columnas = {"ID", "Asunto", "Fecha", "Prioridad", "Estado Actual"};
        Object[][] datos = {
            {"REQ-02", "Ruido molesto", "19/06/2026", "Media", "En Curso"},
            {"REQ-05", "Poda de árbol frontal", "10/05/2026", "Baja", "Resuelta"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void estilizarBotonPrimario(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 40));
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(35);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(Color.WHITE);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(229, 231, 235));
        tabla.setSelectionBackground(new Color(224, 231, 255));
        tabla.setSelectionForeground(COLOR_TEXTO_OSCURO);
    }
}