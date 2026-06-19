package patrones.state;

import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class EstadoEnCurso implements IEstadoSolicitud {

    public EstadoEnCurso() {
        
    }

    @Override
    public String obtenerNombre() {
        return "En curso";
    }

    @Override
    public String asignarEmpleado(Solicitud s) {
        s.setEmpleado(s.getAsignador().obtenerSiguienteEmpleado());
        return "Se asigno el empleado: " + s.getEmpleado().getNombre();
    }

        @Override
    public String avanzarSolicitud(Solicitud s) {
        s.setEstado(new EstadoResuelta());
        return "La solicitud ahora esta resuelta";
    }
}
