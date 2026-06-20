package vistas;

import javax.swing.*;
import java.awt.*;

import paneles.PanelMisExpensas;
import paneles.PanelMisReclamos;
import paneles.PanelMisReservas;
import patrones.facade.AdministracionFacade;

public class DashboardResidente extends JFrame {

    private final AdministracionFacade fachada;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel centro = new JPanel(cardLayout);

    public DashboardResidente(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Panel de Residente");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        centro.setBackground(UI.FONDO);
        centro.add(new PanelMisExpensas(fachada), "Expensas");
        centro.add(new PanelMisReservas(fachada), "Reservas");
        centro.add(new PanelMisReclamos(fachada), "Reclamos");

        add(Sidebar.construir("MI PERFIL", "Lote L-02 | Juan Perez", this::cerrar,
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
