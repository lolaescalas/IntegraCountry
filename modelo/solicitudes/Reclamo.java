package modelo.solicitudes;
import enums.Prioridad;
import modelo.abstractas.Solicitud;
import patrones.state.IEstadoSolicitud;

public class Reclamo extends Solicitud {

    privaye Prioridad prioridad;

    public Prioridad getPrioridad() {
        return prioridad; }

    public void setPrioridad(Prioridad prioridad) {
         this.prioridad = prioridad; }

}

