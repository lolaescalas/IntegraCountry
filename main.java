import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import modelo.espacios.Barrio;
import patrones.facade.AdministracionFacade;
import vistas.VentanaIngreso;

public class Main {
    public static void main(String[] args) {

        System.out.println("===== INICIANDO SISTEMA INTEGRACOUNTRY =====");
        Barrio barrio = new Barrio("Los Robles", "Av. Principal 123");

        AdministracionFacade administracion = new AdministracionFacade(barrio);
        
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new VentanaIngreso(administracion).setVisible(true));
    }
}
