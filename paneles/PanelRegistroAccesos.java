package paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import patrones.facade.AdministracionFacade;

public class PanelRegistroAccesos extends JPanel {

    private final Color COLOR_FONDO_CENTRAL = new Color(249, 250, 251);
    private final Color COLOR_PRIMARIO = new Color(16, 185, 129); 
    private final Color COLOR_TEXTO_OSCURO = new Color(15, 23, 42);
    private AdministracionFacade fachada;

    private JTextField txtDni;
    private JTextField txtNombre;
    private JComboBox<String> cmbLote;
    private JComboBox<String> cmbTipo;

    public PanelRegistroAccesos(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_CENTRAL);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Registro de Accesos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        
        JLabel lblDni = new JLabel("DNI:"); lblDni.setFont(fontLabel);
        txtDni = new JTextField(); txtDni.setPreferredSize(new Dimension(150, 35));
        
        JLabel lblNombre = new JLabel("Nombre Completo:"); lblNombre.setFont(fontLabel);
        txtNombre = new JTextField(); txtNombre.setPreferredSize(new Dimension(200, 35));
        
        JLabel lblLote = new JLabel("Lote Destino:"); lblLote.setFont(fontLabel);
        cmbLote = new JComboBox<>(new String[]{"L-01", "L-02", "L-03"}); cmbLote.setPreferredSize(new Dimension(100, 35));
        
        JLabel lblTipo = new JLabel("Tipo:"); lblTipo.setFont(fontLabel);
        cmbTipo = new JComboBox<>(new String[]{"Visita", "Proveedor", "Delivery"}); cmbTipo.setPreferredSize(new Dimension(150, 35));

        JButton btnRegistrar = new JButton("Registrar Ingreso");
        estilizarBotonPrimario(btnRegistrar);

        gbc.gridy = 0;
        gbc.gridx = 0; panelFormulario.add(lblDni, gbc);
        gbc.gridx = 1; panelFormulario.add(txtDni, gbc);
        gbc.gridx = 2; panelFormulario.add(lblNombre, gbc);
        gbc.gridx = 3; panelFormulario.add(txtNombre, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; panelFormulario.add(lblLote, gbc);
        gbc.gridx = 1; panelFormulario.add(cmbLote, gbc);
        gbc.gridx = 2; panelFormulario.add(lblTipo, gbc);
        gbc.gridx = 3; panelFormulario.add(cmbTipo, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0; gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> {
          
            JOptionPane.showMessageDialog(this, "Ingreso registrado con éxito.");
            txtDni.setText(""); txtNombre.setText("");
        });

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);

        String[] columnas = {"Hora", "DNI", "Nombre", "Lote", "Tipo"};
        Object[][] datos = {
            {"08:15", "30111222", "Carlos Repartidor", "L-02", "Delivery"},
            {"09:30", "28333444", "Ana Gomez", "L-01", "Visita"}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        estilizarTabla(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void estilizarBotonPrimario(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(250, 45));
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(35);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(Color.WHITE);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(229, 231, 235));
        tabla.setSelectionBackground(new Color(209, 250, 229)); // Verde clarito
        tabla.setSelectionForeground(COLOR_TEXTO_OSCURO);
    }
}