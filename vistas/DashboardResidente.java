package vistas;

import javax.swing.*;
import java.awt.*;

import paneles.PanelMisExpensas;
import paneles.PanelMisReclamos;
import paneles.PanelMisReservas;
import patrones.facade.AdministracionFacade;

public class DashboardResidente extends JFrame {

    private final AdministracionFacade fachada;
    private final Sesion sesion;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel centro = new JPanel(cardLayout);

    public DashboardResidente(AdministracionFacade fachada, Sesion sesion) {
        this.fachada = fachada;
        this.sesion = sesion;
        setTitle("IntegraCountry - Panel de Residente");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        centro.setBackground(UI.FONDO);
        centro.add(new PanelMisExpensas(fachada), "Expensas");
        centro.add(new PanelMisReservas(fachada, sesion), "Reservas");
        centro.add(new PanelMisReclamos(fachada, sesion), "Reclamos");

        String subtitulo = sesion.getUsuario() != null ? sesion.getUsuario().getNombre() : "Residente";

        add(Sidebar.construir("MI PERFIL", subtitulo, this::cerrar,
                new Sidebar.Item("Mis Expensas", () -> cardLayout.show(centro, "Expensas")),
                new Sidebar.Item("Mis Reservas", () -> cardLayout.show(centro, "Reservas")),
                new Sidebar.Item("Mis Reclamos", () -> cardLayout.show(centro, "Reclamos"))
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
