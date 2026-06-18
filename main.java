import javax.swing.SwingUtilities;

import vistas.VentanaIngreso;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaIngreso ventana = new VentanaIngreso();
            ventana.setVisible(true); 
        });
    }
}
