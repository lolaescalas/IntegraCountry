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
                g2.setPaint(new GradientPaint(0, 0, new Color(30, 58, 138), getWidth(), getHeight(), new Color(15, 23, 42)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        JLabel marca = new JLabel("INTEGRA COUNTRY");
        marca.setFont(new Font("Segoe UI", Font.BOLD, 46));
        marca.setForeground(Color.WHITE);
        marca.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel slogan = new JLabel("Gestión inteligente para tu barrio");
        slogan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        slogan.setForeground(new Color(148, 163, 184));
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
        card.setPreferredSize(new Dimension(460, 580));
        card.setMaximumSize(new Dimension(460, 580));

        JLabel bienv = new JLabel("Bienvenido de nuevo");
        bienv.setFont(new Font("Segoe UI", Font.BOLD, 26));
        bienv.setForeground(UI.TEXTO);
        bienv.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = new JLabel("Ingresá tus datos para acceder al sistema.");
        sub.setFont(UI.BODY);
        sub.setForeground(UI.TEXTO_SUAVE);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension campo = new Dimension(Integer.MAX_VALUE, 44);
        JComboBox<String> roles = new JComboBox<>(new String[]{"Administrador", "Guardia", "Residente"});
        roles.setMaximumSize(campo); roles.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Selector de residente (solo visible si el rol es Residente)
        JLabel lblResidente = etiqueta("RESIDENTE");
        JComboBox<Usuario> residentes = new JComboBox<>();
        for (Usuario u : fachada.getResidentes()) residentes.addItem(u);
        residentes.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Usuario u) setText(u.getNombre());
                return this;
            }
        });
        residentes.setMaximumSize(campo); residentes.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField usuario = new JTextField(); usuario.setMaximumSize(campo); usuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPasswordField pass = new JPasswordField(); pass.setMaximumSize(campo); pass.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton ingresar = UI.boton("Ingresar a la cuenta", UI.PRIMARIO);
        ingresar.setMaximumSize(campo); ingresar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // El selector de residente solo aparece cuando el rol es "Residente"
        Runnable actualizarVisibilidad = () -> {
            boolean esResidente = "Residente".equals(roles.getSelectedItem());
            lblResidente.setVisible(esResidente);
            residentes.setVisible(esResidente);
        };
        roles.addActionListener(e -> actualizarVisibilidad.run());

        card.add(bienv);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(sub);
        card.add(Box.createRigidArea(new Dimension(0, 28)));
        card.add(etiqueta("TIPO DE USUARIO")); card.add(Box.createRigidArea(new Dimension(0, 6))); card.add(roles);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(lblResidente); card.add(Box.createRigidArea(new Dimension(0, 6))); card.add(residentes);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(etiqueta("USUARIO")); card.add(Box.createRigidArea(new Dimension(0, 6))); card.add(usuario);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(etiqueta("CONTRASEÑA")); card.add(Box.createRigidArea(new Dimension(0, 6))); card.add(pass);
        card.add(Box.createRigidArea(new Dimension(0, 28)));
        card.add(ingresar);

        actualizarVisibilidad.run();

        der.add(card);
        add(izq);
        add(der);

        ingresar.addActionListener(e -> {
            if (usuario.getText().isBlank() || new String(pass.getPassword()).isBlank()) {
                JOptionPane.showMessageDialog(this, "Complete usuario y contraseña.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String rol = (String) roles.getSelectedItem();
            Usuario logueado = "Residente".equals(rol) ? (Usuario) residentes.getSelectedItem() : null;
            Sesion sesion = new Sesion(rol, logueado);

            JFrame dash = switch (rol) {
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
