package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modelo.espacios.Reserva;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelGestionReservas extends JPanel {

    private final AdministracionFacade fachada;
    private DefaultTableModel modelo;
    private JTable tabla;
    private List<Reserva> filas;

    public PanelGestionReservas(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UI.titulo("Gestión de Reservas"), BorderLayout.WEST);

        String[] cols = {"ID", "Espacio", "Fecha", "Hora", "Solicitante", "Estado"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modelo);
        
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        acciones.setOpaque(false);
        JButton btnAceptar = UI.boton("Aceptar", UI.EXITO);
        JButton btnRechazar = UI.boton("Rechazar", UI.PELIGRO);
        JButton btnRefrescar = UI.boton("Refrescar", UI.TEXTO_SUAVE);
        
        acciones.add(new JLabel("Reserva seleccionada:"));
        acciones.add(btnAceptar); acciones.add(btnRechazar); acciones.add(btnRefrescar);
        btnAceptar.addActionListener(e -> decidir(true));
        btnRechazar.addActionListener(e -> decidir(false));
        btnRefrescar.addActionListener(e -> refrescar());

        add(top, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);
        refrescar();
    }

    private void decidir(boolean aceptar) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva de la tabla primero.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Reserva r = filas.get(fila);
        if (aceptar) fachada.aceptarReserva(r); else fachada.rechazarReserva(r);
        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        filas = fachada.getReservas();
        for (Reserva r : filas) {
            modelo.addRow(new Object[]{ "RES-" + r.getId(), r.getEspacio(), r.getFecha(),
                    r.getHorario(), r.getNombreSolicitante(), r.getEstado() });
        }
    }
}
