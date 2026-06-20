package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import modelo.espacios.Autorizacion;
import modelo.espacios.Ingreso;
import modelo.espacios.Lote;
import patrones.facade.AdministracionFacade;
import vistas.UI;

public class PanelRegistroAccesos extends JPanel {

    private final AdministracionFacade fachada;
    private JTextField txtDni, txtNombre;
    private JComboBox<String> cmbLote, cmbTipo, cmbMovimiento;
    private DefaultTableModel modeloAccesos;
    private DefaultTableModel modeloAutorizadas;

    public PanelRegistroAccesos(AdministracionFacade fachada) {
        this.fachada = fachada;
        setLayout(new BorderLayout(0, 20));
        setBackground(UI.FONDO);
        setBorder(UI.marcoPanel());

        // ---------- Formulario ----------
        JPanel form = UI.card(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL; g.anchor = GridBagConstraints.WEST;

        txtDni = new JTextField(12);
        txtNombre = new JTextField(18);
        cmbLote = new JComboBox<>();
        for (Lote l : fachada.getLotes()) cmbLote.addItem(l.getEtiqueta());
        cmbTipo = new JComboBox<>(new String[]{"Residente", "Visita", "Proveedor", "Delivery"});
        cmbMovimiento = new JComboBox<>(new String[]{"INGRESO", "EGRESO"});

        // Cuando el guardia termina de tipear el DNI, intenta autocompletar lote y nombre
        txtDni.addFocusListener(new FocusAdapter() {
            @Override public void focusLost(FocusEvent e) { autocompletarPorDni(); }
        });

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
        JButton btnRef = UI.boton("Refrescar", UI.TEXTO_SUAVE);
        btnRef.addActionListener(e -> refrescar());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        botones.add(btnRef); botones.add(btn);
        g.gridx=2; g.gridy=3; g.gridwidth=2; g.fill=GridBagConstraints.NONE; g.anchor=GridBagConstraints.EAST;
        form.add(botones, g);

        // ---------- Tabla de visitas autorizadas (lo que el guardia consulta) ----------
        String[] colsAut = {"Nombre", "DNI", "Tipo", "Lote", "Autorizada por"};
        modeloAutorizadas = new DefaultTableModel(colsAut, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        JPanel panelAut = new JPanel(new BorderLayout(0, 8));
        panelAut.setOpaque(false);
        JLabel lblAut = new JLabel("Visitas autorizadas (pendientes de ingreso)");
        lblAut.setFont(UI.BOLD);
        panelAut.add(lblAut, BorderLayout.NORTH);
        panelAut.add(UI.tabla(new JTable(modeloAutorizadas)), BorderLayout.CENTER);

        // ---------- Tabla de accesos registrados ----------
        String[] colsAcc = {"Hora", "Movimiento", "Tipo", "Nombre", "DNI", "Lote"};
        modeloAccesos = new DefaultTableModel(colsAcc, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        JPanel panelAcc = new JPanel(new BorderLayout(0, 8));
        panelAcc.setOpaque(false);
        JLabel lblAcc = new JLabel("Historial de accesos");
        lblAcc.setFont(UI.BOLD);
        panelAcc.add(lblAcc, BorderLayout.NORTH);
        panelAcc.add(UI.tabla(new JTable(modeloAccesos)), BorderLayout.CENTER);

        JPanel tablas = new JPanel(new GridLayout(2, 1, 0, 16));
        tablas.setOpaque(false);
        tablas.add(panelAut);
        tablas.add(panelAcc);

        JPanel norte = new JPanel(new BorderLayout(0, 16));
        norte.setOpaque(false);
        norte.add(UI.titulo("Registro de Accesos"), BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);

        add(norte, BorderLayout.NORTH);
        add(tablas, BorderLayout.CENTER);

        refrescar();
    }

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(UI.BOLD); return l; }

    // Si hay una autorizacion vigente para ese DNI, completa nombre y lote solos
    private void autocompletarPorDni() {
        String dni = txtDni.getText().trim();
        if (dni.isEmpty()) return;
        Autorizacion a = fachada.buscarAutorizacionPorDni(dni);
        if (a != null) {
            txtNombre.setText(a.getNombreVisita());
            cmbLote.setSelectedItem(a.getLote().getEtiqueta());
            cmbTipo.setSelectedItem(a.getTipo());
        }
    }

    private void registrar() {
        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (dni.isBlank() || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "Complete DNI y nombre.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cmbLote.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay lotes cargados en el barrio.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String resultado = fachada.registrarAcceso(
                hora, (String) cmbTipo.getSelectedItem(), (String) cmbMovimiento.getSelectedItem(),
                nombre, dni, (String) cmbLote.getSelectedItem());

        if (resultado.startsWith("OK")) {
            JOptionPane.showMessageDialog(this, resultado, "Acceso permitido", JOptionPane.INFORMATION_MESSAGE);
            txtDni.setText(""); txtNombre.setText("");
            refrescar();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescar() {
        // Visitas autorizadas vigentes (con su lote, para que el guardia sepa adonde van)
        modeloAutorizadas.setRowCount(0);
        for (Autorizacion a : fachada.getAutorizacionesVigentes()) {
            modeloAutorizadas.addRow(new Object[]{
                    a.getNombreVisita(), a.getDniVisita(), a.getTipo(),
                    a.getLote().getEtiqueta(), a.getNombreAutorizante() });
        }
        // Historial de accesos
        modeloAccesos.setRowCount(0);
        for (Ingreso i : fachada.getAccesos()) {
            modeloAccesos.addRow(new Object[]{ i.getHora(), i.getMovimiento(), i.getTipo(),
                    i.getNombrePersona(), i.getDniPersona(), i.getDestino() });
        }
    }
}
