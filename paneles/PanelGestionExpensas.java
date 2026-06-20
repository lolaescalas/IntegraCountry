package paneles;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import patrones.facade.AdministracionFacade;

public class PanelGestionExpensas extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(79, 70, 229);
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);
    private AdministracionFacade fachada;

    public PanelGestionExpensas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Control de Expensas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JButton btnGenerar = new JButton("Generar Expensas del Mes");
        estilizarBotonPrimario(btnGenerar);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        panelTop.add(lblTitulo, BorderLayout.WEST);
        panelTop.add(btnGenerar, BorderLayout.EAST);

        String[] columnas = {"Lote", "Titular", "Período", "Monto Base", "Multas", "Total", "Estado"};
        Object[][] datos = {
            {"L-01", "Juan Perez", "05/2026", "$50000", "$0", "$50000", "Pagado"},
            {"L-02", "Maria Gomez", "05/2026", "$50000", "$5000", "$55000", "Vencido"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
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