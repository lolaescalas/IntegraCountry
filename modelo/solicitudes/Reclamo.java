package modelo.solicitudes;
import enums.Prioridad;
import modelo.abstractas.Solicitud;

public class Reclamo extends Solicitud {

    private Prioridad prioridad;

    public Prioridad getPrioridad() {
        return prioridad; }

    public void setPrioridad(Prioridad prioridad) {
         this.prioridad = prioridad; }

}

