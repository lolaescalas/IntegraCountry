import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import modelo.espacios.Barrio;
import modelo.espacios.Lote;
import patrones.facade.AdministracionFacade;
import vistas.VentanaIngreso;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== INICIANDO SISTEMA INTEGRACOUNTRY =====");

        Barrio barrio = new Barrio("Los Robles", "Av. Principal 123");
        // Lotes iniciales del barrio (fuente unica: de aca salen los combos y los residentes)
        barrio.agregarLote(new Lote(1, "Activo"));
        barrio.agregarLote(new Lote(2, "Activo"));
        barrio.agregarLote(new Lote(3, "Moroso"));

        AdministracionFacade administracion = new AdministracionFacade(barrio);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new VentanaIngreso(administracion).setVisible(true));
    }
}