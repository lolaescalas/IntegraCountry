package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.espacios.Barrio;
import paneles.PanelGestionResidentes;
import patrones.facade.AdministracionFacade;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardAdmin extends JFrame {

    // Componentes principales
    private JPanel panelMenuLateral;
    private JPanel panelCentral;
    private CardLayout cardLayout;
    private AdministracionFacade fachada;

    private final Color COLOR_SIDEBAR = new Color(15, 23, 42);       
    private final Color COLOR_SIDEBAR_HOVER = new Color(30, 41, 59); 
    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(79, 70, 229);     
    private final Color COLOR_TEXTO_CLARO = new Color(248, 250, 252);
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);

    public DashboardAdmin(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Panel de Administración");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(COLOR_FONDO_CENTRAL);

        panelCentral.add(new PanelGestionResidentes(fachada), "Residentes");
        panelCentral.add(crearPanelSolicitudes(), "Solicitudes");
        panelCentral.add(crearPanelExpensas(), "Expensas");

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

        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSub = new JLabel("IntegraCountry");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenuLateral.add(lblTitulo);
        panelMenuLateral.add(lblSub);
        panelMenuLateral.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnResidentes = crearBotonMenu("Gestión de Residentes");
        JButton btnSolicitudes = crearBotonMenu("Gestión de Solicitudes");
        JButton btnExpensas = crearBotonMenu("Gestión de Expensas");
        
        panelMenuLateral.add(btnResidentes);
        panelMenuLateral.add(btnSolicitudes);
        panelMenuLateral.add(btnExpensas);
        
        panelMenuLateral.add(Box.createVerticalGlue());
        
        JButton btnSalir = crearBotonMenu("Cerrar Sesión");
        btnSalir.setForeground(new Color(239, 68, 68));
        panelMenuLateral.add(btnSalir);

        btnResidentes.addActionListener(e -> cardLayout.show(panelCentral, "Residentes"));
        btnSolicitudes.addActionListener(e -> cardLayout.show(panelCentral, "Solicitudes"));
        btnExpensas.addActionListener(e -> cardLayout.show(panelCentral, "Expensas"));
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); // Cierra este dashboard
                // new VentanaIngreso().setVisible(true); // Abre el login (Descomentar cuando lo unas)
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

    private JPanel crearPanelResidentes() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(COLOR_FONDO_CENTRAL);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Cabecera
        JLabel lblTitulo = new JLabel("Gestión de Residentes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JButton btnNuevo = new JButton("+ Nuevo Residente");
        estilizarBotonPrimario(btnNuevo);
        
        btnNuevo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Utilice el panel de gestión para crear residentes.", "Información", JOptionPane.INFORMATION_MESSAGE));

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        panelTop.add(lblTitulo, BorderLayout.WEST);
        panelTop.add(btnNuevo, BorderLayout.EAST);

        String[] columnas = {"Lote", "Nombre", "DNI", "Tipo", "Estado"};
        Object[][] datos = {
            {"L-01", "Juan Perez", "11222333", "Propietario", "Activo"},
            {"L-02", "Maria Gomez", "44555666", "Inquilino", "Activo"},
            {"L-03", "Carlos Lopez", "77888999", "Propietario", "Moroso"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelSolicitudes() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(COLOR_FONDO_CENTRAL);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Solicitudes y Reclamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(lblTitulo);

        String[] columnas = {"ID", "Lote", "Tipo", "Descripción", "Estado Actual"};
        Object[][] datos = {
            {"REQ-01", "L-02", "Mantenimiento", "Foco fundido en calle 3", "Pendiente"},
            {"REQ-02", "L-15", "Reclamo", "Ruido molesto", "En Curso"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelAcciones.setOpaque(false);
        panelAcciones.add(new JLabel("Cambiar estado de solicitud seleccionada a:"));
        
        JComboBox<String> comboEstados = new JComboBox<>(new String[]{"Pendiente", "En Curso", "Resuelta"});
        JButton btnActualizar = new JButton("Actualizar Estado");
        estilizarBotonPrimario(btnActualizar);
        
        

        panelAcciones.add(comboEstados);
        panelAcciones.add(btnActualizar);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(panelAcciones, BorderLayout.SOUTH);
        return panel;
    }

   
    private JPanel crearPanelExpensas() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(COLOR_FONDO_CENTRAL);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Control de Expensas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JButton btnGenerar = new JButton("Generar Expensas del Mes");
        estilizarBotonPrimario(btnGenerar);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        panelTop.add(lblTitulo, BorderLayout.WEST);
        panelTop.add(btnGenerar, BorderLayout.EAST);

        String[] columnas = {"Lote", "Titular", "Período", "Monto Base", "Multas", "Total", "Estado"};
        Object[][] datos = {
            {"L-01", "Juan Perez", "05/2026", "$50000", "$0", "$50000", "Pagado"},
            {"L-02", "Maria Gomez", "05/2026", "$50000", "$5000", "$55000", "Vencido"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    
    private void estilizarBotonPrimario(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 40));
    }

    
    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(35);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(Color.WHITE);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(229, 231, 235));
        tabla.setSelectionBackground(new Color(224, 231, 255));
        tabla.setSelectionForeground(COLOR_TEXTO_OSCURO);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new DashboardAdmin(new AdministracionFacade(new Barrio("Los Robles", "Av. Principal 123"))).setVisible(true));
    }
}