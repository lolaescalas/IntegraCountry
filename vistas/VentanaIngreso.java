package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import modelo.abstractas.Usuario;
import patrones.facade.AdministracionFacade;

public class VentanaIngreso extends JFrame {

    private final AdministracionFacade fachada;

    public VentanaIngreso(AdministracionFacade fachada) {
        this.fachada = fachada;
        setTitle("IntegraCountry - Acceso");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel izq = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //g2.setPaint(new GradientPaint(0, 0, new Color(30, 58, 138), getWidth(), getHeight(), new Color(15, 23, 42)));
                g2.setPaint(new GradientPaint(
    0, 0, new Color(255, 183, 77),
    getWidth(), getHeight(),
    new Color(255, 138, 101)
));
                g2.fillRect(0, 0, getWidth(), getHeight());
                
            }
        };
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        // Logo desde la carpeta recursos (con fondo transparente)
        JLabel marca = new JLabel();
        try {
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(new java.io.File("recursos/logo.png"));
            // Escalamos el logo a 300px de ancho manteniendo proporción
            int ancho = 800;
            int alto = img.getHeight() * ancho / img.getWidth();
            Image escalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            marca.setIcon(new ImageIcon(escalada));
        } catch (Exception ex) {
            // Si no encuentra la imagen, muestra el texto como respaldo
            marca.setText("INTEGRA COUNTRY");
            marca.setFont(new Font("Segoe UI", Font.BOLD, 46));
            marca.setForeground(Color.WHITE);
        }
        marca.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slogan = new JLabel("Gestión inteligente para tu barrio");
        slogan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        slogan.setForeground(new Color(30, 58, 138));
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        textos.add(marca);
        textos.add(Box.createRigidArea(new Dimension(0, 10)));
        textos.add(slogan);
        izq.add(textos);

        JPanel der = new JPanel(new GridBagLayout());
        der.setBackground(UI.FONDO);
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UI.BORDE, 1, true),
                new EmptyBorder(48, 48, 48, 48)));
        card.setPreferredSize(new Dimension(460, 500));
        card.setMaximumSize(new Dimension(460, 500));

        JLabel bienv = new JLabel("Bienvenido de nuevo");
        bienv.setFont(new Font("Segoe UI", Font.BOLD, 26));
        bienv.setForeground(UI.TEXTO);
        bienv.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = new JLabel("Ingresá tu usuario y contraseña.");
        sub.setFont(UI.BODY);
        sub.setForeground(UI.TEXTO_SUAVE);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension campo = new Dimension(Integer.MAX_VALUE, 44);
        JTextField usuario = new JTextField(); usuario.setMaximumSize(campo); usuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPasswordField pass = new JPasswordField(); pass.setMaximumSize(campo); pass.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton ingresar = UI.boton("Ingresar", UI.PRIMARIO);
        ingresar.setMaximumSize(campo); ingresar.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(bienv);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(sub);
        card.add(Box.createRigidArea(new Dimension(0, 28)));
        card.add(etiqueta("USUARIO")); card.add(Box.createRigidArea(new Dimension(0, 6))); card.add(usuario);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(etiqueta("CONTRASEÑA")); card.add(Box.createRigidArea(new Dimension(0, 6))); card.add(pass);
        card.add(Box.createRigidArea(new Dimension(0, 28)));
        card.add(ingresar);

        der.add(card);
        add(izq);
        add(der);

        ingresar.addActionListener(e -> {
            String u = usuario.getText().trim();
            String p = new String(pass.getPassword());
            if (u.isBlank() || p.isBlank()) {
                JOptionPane.showMessageDialog(this, "Complete usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario logueado = fachada.autenticar(u, p);
            if (logueado == null) {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Sesion sesion = new Sesion(logueado);
            JFrame dash = switch (logueado.getRol()) {
                case "Administrador" -> new DashboardAdmin(fachada, sesion);
                case "Guardia" -> new DashboardGuardia(fachada, sesion);
                default -> new DashboardResidente(fachada, sesion);
            };
            dash.setVisible(true);
            dispose();
        });
    }

    private JLabel etiqueta(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(55, 65, 81));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
}
