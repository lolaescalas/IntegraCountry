package tests;
import patrones.strategy.CanalEmail;
import patrones.strategy.CanalInterno;
import patrones.strategy.CanalSMS;
import patrones.strategy.Notificacion;

public class StrategyTest {

    public static void main(String[] args) {

        Notificacion notificacion = new Notificacion(new CanalEmail());
        notificacion.actualizar("Su reclamo fue registrado.");

        notificacion.setCanal(new CanalSMS());
        notificacion.actualizar("Su reclamo esta en curso.");
        
        notificacion.setCanal(new CanalInterno());
        notificacion.actualizar("Su reclamo fue resuelto.");

        System.out.println("Ultimo: " + notificacion.getMensaje() + " - " + notificacion.getFecha());
    }
}
