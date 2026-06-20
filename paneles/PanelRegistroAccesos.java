package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelRegistroAccesos extends JPanel {

    private final AdministracionFacade fachada;
    private JTextField txtDni, txtNombre;
    private JComboBox<String> cmbLote, cmbTipo;
    private DefaultTableModel modelo;

    public PanelRegistroAccesos(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 24));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        JPanel form = UI.card(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL; g.anchor = GridBagConstraints.WEST;

        txtDni = new JTextField(12);
        txtNombre = new JTextField(18);
        cmbLote = new JComboBox<>(new String[]{"L-01", "L-02", "L-03"});
        cmbTipo = new JComboBox<>(new String[]{"Visita", "Proveedor", "Delivery"});

        g.gridx=0; g.gridy=0; form.add(lbl("DNI:"), g);
        g.gridx=1; form.add(txtDni, g);
        g.gridx=2; form.add(lbl("Nombre:"), g);
        g.gridx=3; form.add(txtNombre, g);
        g.gridx=0; g.gridy=1; form.add(lbl("Lote destino:"), g);
        g.gridx=1; form.add(cmbLote, g);
        g.gridx=2; form.add(lbl("Tipo:"), g);
        g.gridx=3; form.add(cmbTipo, g);

        JButton btn = UI.boton("Registrar Ingreso", UI.EXITO);
        btn.addActionListener(e -> registrar());
        g.gridx=3; g.gridy=2; g.fill=GridBagConstraints.NONE; g.anchor=GridBagConstraints.EAST;
        form.add(btn, g);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Registro de Accesos"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        String[] cols = {"Hora", "DNI", "Nombre", "Lote", "Tipo"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        add(norte, BorderLayout.NORTH);
        add(UI.tabla(tabla), BorderLayout.CENTER);
    }

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l; }

    private void registrar() {
        if (txtDni.getText().isBlank() || txtNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Complete DNI y nombre.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        // Registra el ingreso a traves de la fachada (visita = persona null permitido en el log)
        fachada.registrarIngreso(hora, (String) cmbTipo.getSelectedItem(), null, (String) cmbLote.getSelectedItem());
        modelo.addRow(new Object[]{hora, txtDni.getText().trim(), txtNombre.getText().trim(),
                cmbLote.getSelectedItem(), cmbTipo.getSelectedItem()});
        txtDni.setText(""); txtNombre.setText("");
    }
}
