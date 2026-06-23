package modelo.solicitudes;
import modelo.enums.TipoServicio;
import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class TareaMantenimiento extends Solicitud {

    private TipoServicio tipoServicio;

    public TareaMantenimiento(AsignadorEmpleado asignador, TipoServicio tipoServicio) {
        super(asignador); 
        this.tipoServicio = tipoServicio; 
    }
    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }
    @Override public String getTipoDescripcion() { return "Mantenimiento"; }
}
