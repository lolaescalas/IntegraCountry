package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelGestionExpensas extends JPanel {

    private final AdministracionFacade fachada;

    public PanelGestionExpensas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JButton btnGenerar = UI.boton("Generar Expensas del Mes", UI.PRIMARIO);
        btnGenerar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Expensas generadas para el período actual."));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UI.titulo("Control de Expensas"), BorderLayout.WEST);
        top.add(btnGenerar, BorderLayout.EAST);

        String[] cols = {"Lote", "Titular", "Período", "Monto Base", "Multas", "Total", "Estado"};
        Object[][] datos = {
            {"L-01", "Juan Perez", "05/2026", "$50000", "$0", "$50000", "Pagado"},
            {"L-02", "Maria Gomez", "05/2026", "$50000", "$5000", "$55000", "Vencido"}
        };
        DefaultTableModel m = new DefaultTableModel(datos, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        add(top, BorderLayout.NORTH);
        add(UI.tabla(new JTable(m)), BorderLayout.CENTER);
    }
}
