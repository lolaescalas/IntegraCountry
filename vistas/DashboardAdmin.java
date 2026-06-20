package vistas;

import javax.swing.*;
import java.awt.*;

import paneles.PanelGestionExpensas;
import paneles.PanelGestionResidentes;
import paneles.PanelGestionSolicitudes;
import patrones.facade.AdministracionFacade;

public class DashboardAdmin extends JFrame {

    private final AdministracionFacade fachada;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel centro = new JPanel(cardLayout);

    public DashboardAdmin(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Panel de Administración");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        centro.setBackground(UI.FONDO);
        centro.add(new PanelGestionResidentes(fachada), "Residentes");
        centro.add(new PanelGestionSolicitudes(fachada), "Solicitudes");
        centro.add(new PanelGestionExpensas(fachada), "Expensas");

        add(Sidebar.construir("ADMINISTRACIÓN", "IntegraCountry", this::cerrar,
                new Sidebar.Item("Gestión de Residentes", () -> mostrar("Residentes")),
                new Sidebar.Item("Gestión de Solicitudes", () -> mostrar("Solicitudes")),
                new Sidebar.Item("Gestión de Expensas", () -> mostrar("Expensas"))
        ), BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
    }

    private void mostrar(String k) { cardLayout.show(centro, k); }

    private void cerrar() {
        if (JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
            new VentanaIngreso(fachada).setVisible(true);
        }
    }
}
