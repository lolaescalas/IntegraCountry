package paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import patrones.facade.AdministracionFacade;

public class PanelMisExpensas extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(79, 70, 229);
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);
    private final Color COLOR_EXITO = new Color(16, 185, 129); 
    private AdministracionFacade fachada;

    public PanelMisExpensas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Mis Expensas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelResumen.setBackground(Color.WHITE);
        panelResumen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblDeudaTexto = new JLabel("Deuda Actual:");
        lblDeudaTexto.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        
        JLabel lblDeudaMonto = new JLabel("$ 55,000.00");
        lblDeudaMonto.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblDeudaMonto.setForeground(new Color(220, 38, 38));

        JButton btnPagar = new JButton("Simular Pago");
        estilizarBoton(btnPagar, COLOR_EXITO);

        btnPagar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pago procesado exitosamente.", "Pago Confirmado", JOptionPane.INFORMATION_MESSAGE);
            lblDeudaMonto.setText("$ 0.00");
            lblDeudaMonto.setForeground(COLOR_EXITO);
        });

        panelResumen.add(lblDeudaTexto);
        panelResumen.add(lblDeudaMonto);
        panelResumen.add(Box.createHorizontalStrut(30));
        panelResumen.add(btnPagar);

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelResumen, BorderLayout.CENTER);

        String[] columnas = {"Período", "Vencimiento", "Monto Base", "Multas", "Total", "Estado"};
        Object[][] datos = {
            {"05/2026", "10/06/2026", "$50000", "$5000", "$55000", "Vencida"},
            {"04/2026", "10/05/2026", "$50000", "$0", "$50000", "Pagada"},
            {"03/2026", "10/04/2026", "$45000", "$0", "$45000", "Pagada"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
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