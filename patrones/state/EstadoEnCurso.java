package patrones.state;

import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class EstadoEnCurso implements IEstadoSolicitud {

    private AsignadorEmpleado asignador;

    @Override
    public String obtenerNombre() {
        return "En curso";
    }

    @Override
    public String asignarEmpleado(Solicitud s) {
        s.setEmpleado(
            asignador.obtenerSiguienteEmpleado()
        );
        return "Se asigno el empleado.";
    }

    @Override
    public String avanzarSolicitud(Solicitud s) {
        return "La solicitud esta resuelta";
    }

    public EstadoEnCurso() {
    }
}
