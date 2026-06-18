package patrones.state;

import modelo.abstractas.Solicitud;

public class EstadoEnCurso implements IEstadoSolicitud {


    @Override
    public String obtenerNombre() {
        return "En curso";
    }

    @Override
    public String asignarEmpleado(Solicitud s) {
        s.setEmpleado(empleado);
        return "Se asigno el empleado" + empleado;
    }

    @Override
    public String manejarSolicitud(Solicitud s) {
        return "La solicitud esta resuelta";
    }

    public EstadoEnCurso() {
    }
}
