package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import patrones.facade.AdministracionFacade;

public class VentanaIngreso extends JFrame {

    private AdministracionFacade fachada; 

    public VentanaIngreso(AdministracionFacade fachada) {
        this.fachada = fachada;

        setTitle("IntegraCountry - Acceso");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel panelIzquierdo = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 58, 138), getWidth(), getHeight(), new Color(15, 23, 42));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel lblBranding = new JLabel("INTEGRA COUNTRY");
        lblBranding.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblBranding.setForeground(Color.WHITE);
        
        JLabel lblSlogan = new JLabel("Gestión inteligente para tu barrio");
        lblSlogan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSlogan.setForeground(new Color(148, 163, 184));
        
        JPanel panelTextosIzq = new JPanel();
        panelTextosIzq.setLayout(new BoxLayout(panelTextosIzq, BoxLayout.Y_AXIS));
        panelTextosIzq.setOpaque(false);
        
        lblBranding.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTextosIzq.add(lblBranding);
        panelTextosIzq.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTextosIzq.add(lblSlogan);
        
        panelIzquierdo.add(panelTextosIzq);

        // MITAD DERECHA: Formulario blanco
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(new Color(249, 250, 251));

        JPanel tarjetaForm = new JPanel();
        tarjetaForm.setLayout(new BoxLayout(tarjetaForm, BoxLayout.Y_AXIS));
        tarjetaForm.setBackground(Color.WHITE);
        tarjetaForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(50, 50, 50, 50)
        ));
        
        Dimension tamañoTarjeta = new Dimension(450, 600);
        tarjetaForm.setPreferredSize(tamañoTarjeta);
        tarjetaForm.setMinimumSize(tamañoTarjeta);
        tarjetaForm.setMaximumSize(tamañoTarjeta);

        JLabel lblBienvenido = new JLabel("Bienvenido de nuevo");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenido.setForeground(new Color(17, 24, 39));
        
        JLabel lblSubtitulo = new JLabel("Ingresa tus datos para acceder al sistema.");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(107, 114, 128));

        Font fuenteLabel = new Font("Segoe UI", Font.BOLD, 13);
        Color colorLabel = new Color(55, 65, 81);
        javax.swing.border.Border bordeCampo = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                new EmptyBorder(12, 15, 12, 15)
        );
        Dimension tamañoCampo = new Dimension(Integer.MAX_VALUE, 45);

        JLabel lblRol = new JLabel("TIPO DE USUARIO");
        lblRol.setFont(fuenteLabel);
        lblRol.setForeground(colorLabel);
        
        JComboBox<String> comboRoles = new JComboBox<>(new String[]{"Administrador", "Guardia", "Residente"});
        comboRoles.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        comboRoles.setBackground(Color.WHITE);
        comboRoles.setMaximumSize(tamañoCampo);

        JLabel lblUsuario = new JLabel("USUARIO");
        lblUsuario.setFont(fuenteLabel);
        lblUsuario.setForeground(colorLabel);
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setBorder(bordeCampo);
        txtUsuario.setMaximumSize(tamañoCampo);

        JLabel lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setFont(fuenteLabel);
        lblPassword.setForeground(colorLabel);
        
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBorder(bordeCampo);
        txtPassword.setMaximumSize(tamañoCampo);

        JButton btnIngresar = new JButton("Ingresar a la cuenta");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnIngresar.setBackground(new Color(79, 70, 229));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setMaximumSize(tamañoCampo);
        
        btnIngresar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnIngresar.setBackground(new Color(67, 56, 202));
            }
            public void mouseExited(MouseEvent evt) {
                btnIngresar.setBackground(new Color(79, 70, 229));
            }
        });

        lblBienvenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRol.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboRoles.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnIngresar.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjetaForm.add(lblBienvenido);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 5)));
        tarjetaForm.add(lblSubtitulo);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 40)));

        tarjetaForm.add(lblRol);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjetaForm.add(comboRoles);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 20)));

        tarjetaForm.add(lblUsuario);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjetaForm.add(txtUsuario);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 20)));

        tarjetaForm.add(lblPassword);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjetaForm.add(txtPassword);
        tarjetaForm.add(Box.createRigidArea(new Dimension(0, 40)));

        tarjetaForm.add(btnIngresar);

        panelDerecho.add(tarjetaForm);

        add(panelIzquierdo);
        add(panelDerecho);

        btnIngresar.addActionListener(e -> {
            String u = txtUsuario.getText();
            String p = new String(txtPassword.getPassword());
            String rol = (String) comboRoles.getSelectedItem();

            if (u.isBlank() || p.isBlank()) {
                JOptionPane.showMessageDialog(this, "Complete usuario y contraseña.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFrame dashboard;
            if (rol.equals("Administrador")) {
                dashboard = new DashboardAdmin(fachada);
            } else if (rol.equals("Guardia")) {
                dashboard = new DashboardGuardia(fachada);
            } else {
                dashboard = new DashboardResidente(fachada);
            }

            dashboard.setVisible(true);
            this.dispose();
        });
    }
}