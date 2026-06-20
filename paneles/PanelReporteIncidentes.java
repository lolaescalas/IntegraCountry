package paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import patrones.facade.AdministracionFacade;

public class PanelReporteIncidentes extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PELIGRO = new Color(239, 68, 68); 
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);
    private AdministracionFacade fachada;

    private JTextArea txtDescripcion;
    private JComboBox<String> cmbRiesgo;

    public PanelReporteIncidentes(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // Cabecera
        JLabel lblTitulo = new JLabel("Reporte de Incidentes de Seguridad");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        // Panel Formulario de Reporte
        JPanel panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblInstrucciones = new JLabel("Describa el incidente con detalle para alertar a la administración:");
        lblInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        txtDescripcion = new JTextArea(5, 40);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelControles.setOpaque(false);
        
        JLabel lblRiesgo = new JLabel("Nivel de Riesgo:");
        lblRiesgo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cmbRiesgo = new JComboBox<>(new String[]{"BAJO", "MEDIO", "ALTO"});
        
        JButton btnReportar = new JButton("Emitir Alerta / Reportar");
        estilizarBotonPeligro(btnReportar);

        btnReportar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Incidente reportado exitosamente.", "Alerta Emitida", JOptionPane.WARNING_MESSAGE);
            txtDescripcion.setText("");
        });

        panelControles.add(lblRiesgo);
        panelControles.add(cmbRiesgo);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(btnReportar);

        panelFormulario.add(lblInstrucciones, BorderLayout.NORTH);
        panelFormulario.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);
        panelFormulario.add(panelControles, BorderLayout.SOUTH);

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);

        String[] columnas = {"Fecha/Hora", "Riesgo", "Descripción", "Estado"};
        Object[][] datos = {
            {"20/06/2026 02:00", "ALTO", "Vehículo sospechoso merodeando el cerco perimetral", "En Revisión"},
            {"19/06/2026 23:15", "BAJO", "Luminaria apagada en entrada principal", "Resuelto"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void estilizarBotonPeligro(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(COLOR_PELIGRO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(250, 40));
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(35);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(Color.WHITE);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(229, 231, 235));
        tabla.setSelectionBackground(new Color(254, 226, 226)); // Rojo clarito
        tabla.setSelectionForeground(COLOR_TEXTO_OSCURO);
    }
}