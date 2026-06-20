package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Menu lateral reutilizable. Antes los tres dashboards repetian este codigo casi identico.
 */
public final class Sidebar {

    private Sidebar() {}

    public record Item(String texto, Runnable accion) {}

    public static JPanel construir(String titulo, String subtitulo, Runnable onSalir, Item... items) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UI.SIDEBAR);
        panel.setPreferredSize(new Dimension(270, 0));
        panel.setBorder(new EmptyBorder(24, 0, 24, 0));

        JLabel lblT = new JLabel(titulo);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblT.setForeground(Color.WHITE);
        lblT.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblS = new JLabel(subtitulo);
        lblS.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblS.setForeground(new Color(148, 163, 184));
        lblS.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblT);
        panel.add(lblS);
        panel.add(Box.createRigidArea(new Dimension(0, 36)));

        for (Item it : items) {
            JButton b = boton(it.texto());
            b.addActionListener(e -> it.accion().run());
            panel.add(b);
        }

        panel.add(Box.createVerticalGlue());

        JButton salir = boton("Cerrar Sesión");
        salir.setForeground(new Color(248, 113, 113));
        salir.addActionListener(e -> onSalir.run());
        panel.add(salir);

        return panel;
    }

    private static JButton boton(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setForeground(new Color(241, 245, 249));
        b.setBackground(UI.SIDEBAR);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(12, 28, 12, 12));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(270, 48));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(UI.SIDEBAR_HOV); }
            public void mouseExited(MouseEvent e) { b.setBackground(UI.SIDEBAR); }
        });
        return b;
    }
}
