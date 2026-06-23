package patrones.strategy;

public class CanalInterno implements CanalNotificacion {
    
    public void enviar(String mensaje) {
        System.out.println("[SISTEMA INTERNO] Enviando: " + mensaje); }
}
