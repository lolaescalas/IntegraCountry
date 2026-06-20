package patrones.strategy;
public class CanalEmail implements CanalNotificacion {
    public void enviar(String mensaje) { System.out.println("[EMAIL] Enviando: " + mensaje); }
}
