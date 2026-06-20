package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.espacios.Barrio;
import paneles.PanelRegistroAccesos;
import paneles.PanelReporteIncidentes;
import patrones.facade.AdministracionFacade;

public class DashboardGuardia extends JFrame {

    private JPanel panelMenuLateral;
    private JPanel panelCentral;
    private CardLayout cardLayout;
    private AdministracionFacade fachada;

    private final Color COLOR_SIDEBAR = new Color(15, 23, 42);       
    private final Color COLOR_SIDEBAR_HOVER = new Color(30, 41, 59); 
    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251); 
    private final Color COLOR_TEXTO_CLARO = new Color(248, 250, 252);

    public DashboardGuardia(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Panel de Guardia / Seguridad");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(COLOR_FONDO_CENTRAL);

        panelCentral.add(new PanelRegistroAccesos(fachada), "Accesos");
        panelCentral.add(new PanelReporteIncidentes(fachada), "Incidentes");

        construirMenuLateral();

        add(panelMenuLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void construirMenuLateral() {
        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new BoxLayout(panelMenuLateral, BoxLayout.Y_AXIS));
        panelMenuLateral.setBackground(COLOR_SIDEBAR);
        panelMenuLateral.setPreferredSize(new Dimension(280, 0));
        panelMenuLateral.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel lblTitulo = new JLabel("PUESTO GUARDIA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSub = new JLabel("Control de Accesos");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenuLateral.add(lblTitulo);
        panelMenuLateral.add(lblSub);
        panelMenuLateral.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnAccesos = crearBotonMenu("Registro de Accesos");
        JButton btnIncidentes = crearBotonMenu("Reporte de Incidentes");
        
        panelMenuLateral.add(btnAccesos);
        panelMenuLateral.add(btnIncidentes);
        
        panelMenuLateral.add(Box.createVerticalGlue());
        
        JButton btnSalir = crearBotonMenu("Cerrar Sesión");
        btnSalir.setForeground(new Color(239, 68, 68)); 
        panelMenuLateral.add(btnSalir);

        btnAccesos.addActionListener(e -> cardLayout.show(panelCentral, "Accesos"));
        btnIncidentes.addActionListener(e -> cardLayout.show(panelCentral, "Incidentes"));
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); 
                new VentanaIngreso().setVisible(true); 
                System.exit(0);
            }
        });
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(COLOR_TEXTO_CLARO);
        btn.setBackground(COLOR_SIDEBAR);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(COLOR_SIDEBAR_HOVER);
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(COLOR_SIDEBAR);
            }
        });
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new DashboardGuardia(new AdministracionFacade(new Barrio("Los Robles", "Av. Principal 123"))).setVisible(true));
    }
}