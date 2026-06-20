package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import modelo.expensas.Expensa;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelGestionExpensas extends JPanel {

    private final AdministracionFacade fachada;
    private DefaultTableModel modelo;

    public PanelGestionExpensas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JButton btnGenerar = UI.boton("Generar Expensas del Mes", UI.PRIMARIO);
        btnGenerar.addActionListener(e -> generar());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UI.titulo("Control de Expensas"), BorderLayout.WEST);
        top.add(btnGenerar, BorderLayout.EAST);

        String[] cols = {"Lote", "Período", "Monto Base", "Multas", "Total", "Estado"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        add(top, BorderLayout.NORTH);
        add(UI.tabla(new JTable(modelo)), BorderLayout.CENTER);
        refrescar();
    }

    private void generar() {
        JTextField periodo = new JTextField("05/2026");
        JTextField monto = new JTextField("50000");
        Object[] form = {"Período (MM/AAAA):", periodo, "Monto base:", monto};
        int op = JOptionPane.showConfirmDialog(this, form, "Generar Expensas", JOptionPane.OK_CANCEL_OPTION);
        if (op != JOptionPane.OK_OPTION) return;
        double base;
        try { base = Double.parseDouble(monto.getText().trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un número.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int creadas = fachada.generarExpensasDelMes(periodo.getText().trim(), base);
        refrescar();
        JOptionPane.showMessageDialog(this, "Se generaron " + creadas + " expensas para " + periodo.getText().trim() + ".");
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Expensa e : fachada.getExpensas()) {
            modelo.addRow(new Object[]{
                    e.getLote().getEtiqueta(), e.getPeriodo(),
                    "$" + (int) e.getMontoBase(), "$" + (int) e.getMultas(),
                    "$" + (int) e.getTotal(), e.getEstado() });
        }
    }
}
