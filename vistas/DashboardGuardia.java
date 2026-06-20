package vistas;

import javax.swing.*;
import java.awt.*;

import paneles.PanelRegistroAccesos;
import paneles.PanelReporteIncidentes;
import patrones.facade.AdministracionFacade;

public class DashboardGuardia extends JFrame {

    private final AdministracionFacade fachada;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel centro = new JPanel(cardLayout);

    public DashboardGuardia(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Panel de Guardia");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        centro.setBackground(UI.FONDO);
        centro.add(new PanelRegistroAccesos(fachada), "Accesos");
        centro.add(new PanelReporteIncidentes(fachada), "Incidentes");

        add(Sidebar.construir("PUESTO GUARDIA", "Control de Accesos", this::cerrar,
                new Sidebar.Item("Registro de Accesos", () -> cardLayout.show(centro, "Accesos")),
                new Sidebar.Item("Reporte de Incidentes", () -> cardLayout.show(centro, "Incidentes"))
        ), BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
    }

    private void cerrar() {
        if (JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
            new VentanaIngreso(fachada).setVisible(true);
        }
    }
}
