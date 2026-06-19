package modelo.solicitudes;
import enums.NivelRiesgo;
import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class IncidenteSeguridad extends Solicitud {
    
    private NivelRiesgo nivelRiesgo;


    public IncidenteSeguridad(AsignadorEmpleado asignador, NivelRiesgo nivelRiesgo) {
        super(asignador);
        this.nivelRiesgo = nivelRiesgo;
    }

    public NivelRiesgo getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }
}



