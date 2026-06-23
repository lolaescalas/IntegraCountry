package modelo.solicitudes;
import modelo.enums.NivelRiesgo;
import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class IncidenteSeguridad extends Solicitud {

    private NivelRiesgo nivelRiesgo; private String descripcion;

    public IncidenteSeguridad(AsignadorEmpleado asignador, NivelRiesgo nivelRiesgo) { super(asignador); this.nivelRiesgo = nivelRiesgo; }

    public NivelRiesgo getNivelRiesgo() { return nivelRiesgo; }

    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    @Override public String getTipoDescripcion() { return "Incidente"; }
}
