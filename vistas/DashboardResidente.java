package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelo.espacios.Barrio;
import paneles.PanelMisExpensas;
import paneles.PanelMisReclamos;
import paneles.PanelMisReservas;
import patrones.facade.AdministracionFacade;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardResidente extends JFrame {

    private JPanel panelMenuLateral;
    private JPanel panelCentral;
    private CardLayout cardLayout;
    private AdministracionFacade fachada;

    private final Color COLOR_SIDEBAR = new Color(15, 23, 42);       
    private final Color COLOR_SIDEBAR_HOVER = new Color(30, 41, 59); 
    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251); 
    private final Color COLOR_TEXTO_CLARO = new Color(248, 250, 252);

    public DashboardResidente(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Panel de Residente");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(COLOR_FONDO_CENTRAL);

        panelCentral.add(new PanelMisExpensas(fachada), "Expensas");
        panelCentral.add(new PanelMisReservas(fachada), "Reservas");
        panelCentral.add(new PanelMisReclamos(fachada), "Reclamos");

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

        JLabel lblTitulo = new JLabel("MI PERFIL");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSub = new JLabel("Lote L-02 | Juan Perez"); // Mock de datos del usuario
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenuLateral.add(lblTitulo);
        panelMenuLateral.add(lblSub);
        panelMenuLateral.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnExpensas = crearBotonMenu("Mis Expensas");
        JButton btnReservas = crearBotonMenu("Mis Reservas");
        JButton btnReclamos = crearBotonMenu("Mis Reclamos");
        
        panelMenuLateral.add(btnExpensas);
        panelMenuLateral.add(btnReservas);
        panelMenuLateral.add(btnReclamos);
        
        panelMenuLateral.add(Box.createVerticalGlue());
        
        JButton btnSalir = crearBotonMenu("Cerrar Sesión");
        btnSalir.setForeground(new Color(239, 68, 68)); 
        panelMenuLateral.add(btnSalir);

        btnExpensas.addActionListener(e -> cardLayout.show(panelCentral, "Expensas"));
        btnReservas.addActionListener(e -> cardLayout.show(panelCentral, "Reservas"));
        btnReclamos.addActionListener(e -> cardLayout.show(panelCentral, "Reclamos"));
        
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); 
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
        SwingUtilities.invokeLater(() -> new DashboardResidente(new AdministracionFacade(new Barrio("Los Robles", "Av. Principal 123"))).setVisible(true));
    }
}