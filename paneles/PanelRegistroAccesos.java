package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import modelo.espacios.Lote;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelRegistroAccesos extends JPanel {

    private final AdministracionFacade fachada;
    private JTextField txtDni, txtNombre;
    private JComboBox<String> cmbLote, cmbTipo, cmbMovimiento;
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

        // Los lotes salen del BARRIO, no estan hardcodeados. Si admin agrega uno, aparece aca.
        cmbLote = new JComboBox<>();
        for (Lote l : fachada.getLotes()) cmbLote.addItem(l.getEtiqueta());

        cmbTipo = new JComboBox<>(new String[]{"Visita", "Proveedor", "Delivery"});
        cmbMovimiento = new JComboBox<>(new String[]{"INGRESO", "EGRESO"});

        g.gridx=0; g.gridy=0; form.add(lbl("Movimiento:"), g);
        g.gridx=1; form.add(cmbMovimiento, g);
        g.gridx=2; form.add(lbl("Tipo:"), g);
        g.gridx=3; form.add(cmbTipo, g);
        g.gridx=0; g.gridy=1; form.add(lbl("DNI:"), g);
        g.gridx=1; form.add(txtDni, g);
        g.gridx=2; form.add(lbl("Nombre:"), g);
        g.gridx=3; form.add(txtNombre, g);
        g.gridx=0; g.gridy=2; form.add(lbl("Lote destino:"), g);
        g.gridx=1; form.add(cmbLote, g);

        JButton btn = UI.boton("Registrar", UI.EXITO);
        btn.addActionListener(e -> registrar());
        g.gridx=3; g.gridy=3; g.fill=GridBagConstraints.NONE; g.anchor=GridBagConstraints.EAST;
        form.add(btn, g);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Registro de Accesos"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        String[] cols = {"Hora", "Movimiento", "DNI", "Nombre", "Lote", "Tipo"};
        modelo = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        add(norte, BorderLayout.NORTH);
        add(UI.tabla(new JTable(modelo)), BorderLayout.CENTER);
    }

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l; }

    private void registrar() {
        if (txtDni.getText().isBlank() || txtNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Complete DNI y nombre.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cmbLote.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay lotes cargados en el barrio.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String mov = (String) cmbMovimiento.getSelectedItem();
        fachada.registrarAcceso(hora, (String) cmbTipo.getSelectedItem(), mov, null, (String) cmbLote.getSelectedItem());
        modelo.addRow(new Object[]{hora, mov, txtDni.getText().trim(), txtNombre.getText().trim(),
                cmbLote.getSelectedItem(), cmbTipo.getSelectedItem()});
        txtDni.setText(""); txtNombre.setText("");
    }
}
