package patrones.strategy;
import java.time.LocalDate;

public class Notificacion {
    
    private String mensaje;
    private LocalDate fecha;
    private CanalNotificacion canal;

    public Notificacion(CanalNotificacion canal) {
        this.canal = canal;
        this.fecha = LocalDate.now();
    }

    public void setCanal(CanalNotificacion canal) {
        this.canal = canal;
    }

    public void actualizar(String msj) {
        this.mensaje = msj;
        this.fecha = LocalDate.now();
        this.canal.enviar(this.mensaje);}
    
    public String getMensaje() { 
        return mensaje; 
    }
    
    public LocalDate getFecha() { 
        return fecha; 
    }
}
