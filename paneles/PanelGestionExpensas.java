package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modelo.expensas.Expensa;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelGestionExpensas extends JPanel {

    private final AdministracionFacade fachada;
    private DefaultTableModel modelo;
    private JTable tabla;              // CAMBIO: ahora guardamos la tabla como campo
    private List<Expensa> filas;       // CAMBIO: guardamos las expensas que se ven, para saber cuál se seleccionó

    public PanelGestionExpensas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JButton btnGenerar = UI.boton("Generar Expensas del Mes", UI.PRIMARIO);
        btnGenerar.addActionListener(e -> generar());

        // CAMBIO: botón nuevo para aplicar multa
        JButton btnMulta = UI.boton("Agregar Multa", UI.PELIGRO);
        btnMulta.addActionListener(e -> agregarMulta());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UI.titulo("Control de Expensas"), BorderLayout.WEST);

        // CAMBIO: los dos botones juntos a la derecha
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        botones.setOpaque(false);
        botones.add(btnMulta);
        botones.add(btnGenerar);
        top.add(botones, BorderLayout.EAST);

        String[] cols = {"Lote", "Período", "Monto Base", "Multas", "Total", "Estado"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modelo);                                  // CAMBIO
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // CAMBIO: se puede seleccionar una fila

        add(top, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);                   // CAMBIO: usamos la tabla guardada
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

    // CAMBIO: método nuevo completo
    private void agregarMulta() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una expensa de la tabla primero.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Expensa e = filas.get(fila);
        String valor = JOptionPane.showInputDialog(this, "Monto de la multa para el lote " + e.getLote().getEtiqueta() + ":", "Agregar Multa", JOptionPane.PLAIN_MESSAGE);
        if (valor == null) return; // canceló
        double monto;
        try { monto = Double.parseDouble(valor.trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un número.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        fachada.agregarMultaAExpensa(e, monto);
        refrescar();
        JOptionPane.showMessageDialog(this, "Multa aplicada. El total se actualizó.");
    }

    private void refrescar() {
        modelo.setRowCount(0);
        filas = fachada.getExpensas();   // CAMBIO: guardamos la lista para la selección
        for (Expensa e : filas) {
            modelo.addRow(new Object[]{
                    e.getLote().getEtiqueta(), e.getPeriodo(),
                    "$" + (int) e.getMontoBase(), "$" + (int) e.getMultas(),
                    "$" + (int) e.getTotal(), e.getEstado() });
        }
    }
}