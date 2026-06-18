package modelo.solicitudes;
import enums.NivelRiesgo;
import modelo.abstractas.Solicitud;

public class IncidenteSeguridad extends Solicitud {
    
    private NivelRiesgo nivelRiesgo;

    public NivelRiesgo getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }
}



