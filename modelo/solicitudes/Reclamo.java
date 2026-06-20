package modelo.solicitudes;
import modelo.enums.Prioridad;
import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class Reclamo extends Solicitud {
    private Prioridad prioridad;
    private String asunto;
    private String descripcion;
    public Reclamo(AsignadorEmpleado asignador, Prioridad prioridad) {
        super(asignador); this.prioridad = prioridad;
    }
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    @Override public String getTipoDescripcion() { return "Reclamo"; }
}
