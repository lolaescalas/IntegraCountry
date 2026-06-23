package patrones.state;
import modelo.abstractas.Solicitud;

public class EstadoPendiente implements IEstadoSolicitud {

    @Override public String obtenerNombre() {
        return "Pendiente"; }

    @Override public String asignarEmpleado(Solicitud s) {
        return "La solicitud esta pendiente, no hay un empleado asignado. Avance la solicitud para asignar empleado.";
    }
    
    @Override public String avanzarSolicitud(Solicitud s) {
        s.setEstado(new EstadoEnCurso());
        String res = s.getEstado().asignarEmpleado(s);
        return "La solicitud ahora esta en curso. " + res;
    }
}
