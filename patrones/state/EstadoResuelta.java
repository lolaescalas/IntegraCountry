package patrones.state;
import modelo.abstractas.Solicitud;
public class EstadoResuelta implements IEstadoSolicitud {
    @Override public String obtenerNombre() { return "Resuelta"; }
    @Override public String avanzarSolicitud(Solicitud s) { return "La solicitud ya esta resuelta"; }
    @Override public String asignarEmpleado(Solicitud s) {
        s.setEmpleado(null);
        return "La solicitud esta resuelta, no hay empleado asignado.";
    }
}
