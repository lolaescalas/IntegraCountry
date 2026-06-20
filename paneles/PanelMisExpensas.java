package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modelo.enums.EstadoExpensa;
import modelo.expensas.Expensa;
import patrones.facade.AdministracionFacade;
import vistas.Sesion;
import vistas.UI;

public class PanelMisExpensas extends JPanel {

    private final AdministracionFacade fachada;
    private final Sesion sesion;
    private JLabel lblMonto;
    private DefaultTableModel modelo;
    private JTable tabla;
    private List<Expensa> filas;

    public PanelMisExpensas(AdministracionFacade fachada, Sesion sesion) {
        this.fachada = fachada;
        this.sesion = sesion;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel resumen = UI.card(new FlowLayout(FlowLayout.LEFT, 16, 4));
        JLabel lblTxt = new JLabel("Deuda actual:"); lblTxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblMonto = new JLabel("$ 0");
        lblMonto.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JButton btnPagar = UI.boton("Pagar Seleccionada", UI.EXITO);
        btnPagar.addActionListener(e -> pagar());
        resumen.add(lblTxt); resumen.add(lblMonto); resumen.add(Box.createHorizontalStrut(24)); resumen.add(btnPagar);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Mis Expensas"), BorderLayout.NORTH);
        norte.add(resumen, BorderLayout.CENTER);

        String[] cols = {"Período", "Monto Base", "Multas", "Total", "Estado"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(norte, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);
        refrescar();
    }

    private void pagar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una expensa de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Expensa e = filas.get(fila);
        if (e.getEstado() == EstadoExpensa.PAGA) {
            JOptionPane.showMessageDialog(this, "Esa expensa ya está paga.", "Atención", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        fachada.pagarExpensa(e);
        refrescar();
        JOptionPane.showMessageDialog(this, "Pago registrado.", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refrescar() {
        modelo.setRowCount(0);
        filas = fachada.getExpensasDeResidente(sesion.getUsuario());
        double deuda = 0;
        for (Expensa e : filas) {
            modelo.addRow(new Object[]{
                    e.getPeriodo(), "$" + (int) e.getMontoBase(), "$" + (int) e.getMultas(),
                    "$" + (int) e.getTotal(), e.getEstado() });
            if (e.getEstado() != EstadoExpensa.PAGA) deuda += e.getTotal();
        }
        lblMonto.setText("$ " + (int) deuda);
        lblMonto.setForeground(deuda > 0 ? UI.PELIGRO : UI.EXITO);
    }
}
