package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelMisExpensas extends JPanel {

    private final AdministracionFacade fachada;

    public PanelMisExpensas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel resumen = UI.card(new FlowLayout(FlowLayout.LEFT, 16, 4));
        JLabel lblTxt = new JLabel("Deuda actual:"); lblTxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        JLabel lblMonto = new JLabel("$ 55.000,00");
        lblMonto.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblMonto.setForeground(UI.PELIGRO);
        JButton btnPagar = UI.boton("Simular Pago", UI.EXITO);
        btnPagar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pago procesado.", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
            lblMonto.setText("$ 0,00"); lblMonto.setForeground(UI.EXITO);
        });
        resumen.add(lblTxt); resumen.add(lblMonto); resumen.add(Box.createHorizontalStrut(24)); resumen.add(btnPagar);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Mis Expensas"), BorderLayout.NORTH);
        norte.add(resumen, BorderLayout.CENTER);

        String[] cols = {"Período", "Vencimiento", "Monto Base", "Multas", "Total", "Estado"};
        Object[][] datos = {
            {"05/2026", "10/06/2026", "$50000", "$5000", "$55000", "Vencida"},
            {"04/2026", "10/05/2026", "$50000", "$0", "$50000", "Pagada"}
        };
        DefaultTableModel m = new DefaultTableModel(datos, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        add(norte, BorderLayout.NORTH);
        add(UI.tabla(new JTable(m)), BorderLayout.CENTER);
    }
}
