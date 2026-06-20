package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modelo.abstractas.Solicitud;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelGestionSolicitudes extends JPanel {

    private final AdministracionFacade fachada;
    private DefaultTableModel modelo;
    private JTable tabla;
    private List<Solicitud> filas;

    public PanelGestionSolicitudes(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UI.titulo("Solicitudes y Reclamos"), BorderLayout.WEST);

        // Columnas: agrego Solicitante (quien la hizo) y Empleado (quien la atiende)
        String[] cols = {"ID", "Tipo", "Asunto/Descripción", "Solicitante", "Empleado", "Estado Actual"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modelo);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        acciones.setOpaque(false);
        JButton btnAvanzar = UI.boton("Avanzar estado", UI.PRIMARIO);
        JButton btnRefrescar = UI.boton("Refrescar", UI.TEXTO_SUAVE);
        acciones.add(new JLabel("Solicitud seleccionada:"));
        acciones.add(btnAvanzar);
        acciones.add(btnRefrescar);
        btnAvanzar.addActionListener(e -> avanzar());
        btnRefrescar.addActionListener(e -> refrescar());

        add(top, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);
        refrescar();
    }

    private void avanzar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud primero.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Solicitud s = filas.get(fila);
        fachada.avanzarSolicitud(s);   // si pasa a Resuelta, desaparece de la vista al refrescar
        refrescar();
    }

    // Solo solicitudes activas (las resueltas quedan en el repositorio pero no se muestran)
    private void refrescar() {
        modelo.setRowCount(0);
        filas = fachada.getSolicitudesActivas();
        for (Solicitud s : filas) {
            String detalle = "";
            if (s instanceof modelo.solicitudes.Reclamo r) detalle = r.getAsunto();
            else if (s instanceof modelo.solicitudes.IncidenteSeguridad i) detalle = i.getDescripcion();
            String empleado = (s.getEmpleado() != null) ? s.getEmpleado().getNombre() : "-";
            modelo.addRow(new Object[]{
                    "REQ-" + s.getId(), s.getTipoDescripcion(), detalle,
                    s.getNombreSolicitante(), empleado, s.getNombreEstado()
            });
        }
    }
}
