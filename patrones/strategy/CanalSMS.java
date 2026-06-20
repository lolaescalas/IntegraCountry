package patrones.strategy;

public class CanalSMS implements CanalNotificacion {
    public void enviar(String mensaje) { System.out.println("[SMS] Enviando: " + mensaje); }
}
