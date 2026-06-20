package paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import patrones.facade.AdministracionFacade;

public class PanelMisReservas extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(79, 70, 229);
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);
    private AdministracionFacade fachada;

    private JComboBox<String> cmbEspacio;
    private JTextField txtFecha;
    private JComboBox<String> cmbHora;

    public PanelMisReservas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Mis Reservas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        
        JLabel lblEspacio = new JLabel("Espacio Común:"); lblEspacio.setFont(fontLabel);
        cmbEspacio = new JComboBox<>(new String[]{"S.U.M.", "Cancha de Tenis", "Cancha de Fútbol", "Parrilla 1"});
        
        JLabel lblFecha = new JLabel("Fecha (DD/MM/AAAA):"); lblFecha.setFont(fontLabel);
        txtFecha = new JTextField(10);
        
        JLabel lblHora = new JLabel("Hora:"); lblHora.setFont(fontLabel);
        cmbHora = new JComboBox<>(new String[]{"10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00"});

        JButton btnReservar = new JButton("Solicitar Reserva");
        estilizarBotonPrimario(btnReservar);

        btnReservar.addActionListener(e -> {
            String espacio = (String) cmbEspacio.getSelectedItem();
            String fecha = txtFecha.getText();
            String hora = (String) cmbHora.getSelectedItem();
            fachada.solicitarReserva(espacio, fecha, hora);
            
            JOptionPane.showMessageDialog(this, "Solicitud de reserva enviada con éxito.");
            txtFecha.setText("");
        });

        panelFormulario.add(lblEspacio); panelFormulario.add(cmbEspacio);
        panelFormulario.add(lblFecha); panelFormulario.add(txtFecha);
        panelFormulario.add(lblHora); panelFormulario.add(cmbHora);
        panelFormulario.add(Box.createHorizontalStrut(20));
        panelFormulario.add(btnReservar);

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);

        String[] columnas = {"Espacio", "Fecha", "Hora", "Estado"};
        Object[][] datos = {
            {"S.U.M.", "25/06/2026", "20:00", "Confirmada"},
            {"Cancha de Tenis", "21/06/2026", "10:00", "Pendiente"}
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
        btn.setPreferredSize(new Dimension(180, 35));
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