package patrones.strategy;
import java.time.LocalDate;
import patrones.observer.IObservador;

public class Notificacion implements IObservador {
    private String mensaje;
    private LocalDate fecha;
    private CanalNotificacion canal;
    public Notificacion(CanalNotificacion canal) { this.canal = canal; this.fecha = LocalDate.now(); }
    public void setCanal(CanalNotificacion canal) { this.canal = canal; }
    @Override
    public void actualizar(String msj) { this.mensaje = msj; this.fecha = LocalDate.now(); this.canal.enviar(this.mensaje); }
    public String getMensaje() { return mensaje; }
    public LocalDate getFecha() { return fecha; }
}
