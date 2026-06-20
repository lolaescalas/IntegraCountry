package paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import patrones.facade.AdministracionFacade;

public class PanelGestionSolicitudes extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(79, 70, 229);
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);
    private AdministracionFacade fachada;

    public PanelGestionSolicitudes(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Solicitudes y Reclamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(lblTitulo);

        String[] columnas = {"ID", "Lote", "Tipo", "Descripción", "Estado Actual"};
        Object[][] datos = {
            {"REQ-01", "L-02", "Mantenimiento", "Foco fundido en calle 3", "Pendiente"},
            {"REQ-02", "L-15", "Reclamo", "Ruido molesto", "En Curso"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelAcciones.setOpaque(false);
        panelAcciones.add(new JLabel("Cambiar estado de solicitud seleccionada a:"));
        
        JComboBox<String> comboEstados = new JComboBox<>(new String[]{"Pendiente", "En Curso", "Resuelta"});
        JButton btnActualizar = new JButton("Actualizar Estado");
        estilizarBotonPrimario(btnActualizar);

        btnActualizar.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                modelo.setValueAt(comboEstados.getSelectedItem(), filaSeleccionada, 4);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una solicitud primero.", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelAcciones.add(comboEstados);
        panelAcciones.add(btnActualizar);

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    private void estilizarBotonPrimario(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 40));
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