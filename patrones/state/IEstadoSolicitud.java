package patrones.state;

import modelo.abstractas.Solicitud;

public interface IEstadoSolicitud {

    String obtenerNombre();
    String asignarEmpleado(Solicitud s);
    String manejarSolicitud(Solicitud s);
}
