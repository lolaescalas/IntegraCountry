package vistas;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Paleta y helpers de estilo centralizados.
 * Antes cada panel repetia colores y metodos de estilo; ahora viven aca (Pure Fabrication para la UI).
 */
public final class UI {

    private UI() {}

    // Paleta
    public static final Color FONDO       = new Color(247, 248, 250);
    public static final Color SIDEBAR     = new Color(17, 24, 39);
    public static final Color SIDEBAR_HOV = new Color(31, 41, 55);
    public static final Color PRIMARIO    = new Color(79, 70, 229);
    public static final Color PRIMARIO_HOV= new Color(67, 56, 202);
    public static final Color EXITO       = new Color(16, 185, 129);
    public static final Color PELIGRO     = new Color(239, 68, 68);
    public static final Color TEXTO       = new Color(17, 24, 39);
    public static final Color TEXTO_SUAVE = new Color(107, 114, 128);
    public static final Color BORDE       = new Color(229, 231, 235);
    public static final Color CARD        = Color.WHITE;

    public static final Font H1   = new Font("Segoe UI", Font.BOLD, 30);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOLD = new Font("Segoe UI", Font.BOLD, 14);

    // Tarjeta blanca con borde suave
    public static JPanel card(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                new EmptyBorder(20, 20, 20, 20)));
        return p;
    }

    // Titulo grande de seccion
    public static JLabel titulo(String t) {
        JLabel l = new JLabel(t);
        l.setFont(H1);
        l.setForeground(TEXTO);
        return l;
    }

    // Boton primario relleno
    public static JButton boton(String texto, Color base) {
        JButton b = new JButton(texto);
        b.setFont(BOLD);
        b.setBackground(base);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 18, 10, 18));
        Color hov = base.darker();
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(hov); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(base); }
        });
        return b;
    }

    public static JButton botonPrimario(String texto) { return boton(texto, PRIMARIO); }

    // Estilo uniforme de tabla
    public static JScrollPane tabla(JTable t) {
        t.setFont(BODY);
        t.setRowHeight(38);
        t.setShowVerticalLines(false);
        t.setGridColor(BORDE);
        t.setSelectionBackground(new Color(224, 231, 255));
        t.setSelectionForeground(TEXTO);
        t.setFillsViewportHeight(true);
        JTableHeader h = t.getTableHeader();
        h.setFont(BOLD);
        h.setBackground(Color.WHITE);
        h.setForeground(TEXTO);
        h.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDE));
        ((DefaultTableCellRenderer) t.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.LEFT);
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(BorderFactory.createLineBorder(BORDE, 1, true));
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    // Marco exterior estandar de cada panel
    public static Border marcoPanel() { return new EmptyBorder(36, 40, 36, 40); }
}

