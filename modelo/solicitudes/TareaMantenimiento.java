package modelo.solicitudes;

import modelo.abstractas.Solicitud;
import patrones.state.IEstadoSolicitud;

public class TareaMantenimiento extends Solicitud {

    private IEstadoSolicitud estado;

    public IEstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(IEstadoSolicitud estado) {
        this.estado = estado;
    }
}
