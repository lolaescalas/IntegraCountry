package patrones.state;

import modelo.abstractas.Solicitud;

public class EstadoPendiente implements IEstadoSolicitud {

    @Override
    public String obtenerNombre() {
        return "Pendiente";
    }

    @Override
    public String asignarEmpleado(Solicitud s) {
        return "La solicitud esta pendiente, no hay un empleado asignado. Manejar solicitud para asignar empleado.";
    }

    @Override
    public String manejarSolicitud(Solicitud s) {
        s.setEstado(new EstadoEnCurso());
        return "La solicitud ahora esta en curso";
    }

}
