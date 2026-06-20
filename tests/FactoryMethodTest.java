package tests;
import modelo.enums.NivelRiesgo;
import modelo.enums.Prioridad;
import modelo.enums.TipoServicio;
import modelo.abstractas.Solicitud;
import patrones.factory.SolicitudFactory;
public class FactoryMethodTest {
    public static void main(String[] args) {
        SolicitudFactory factory = new SolicitudFactory();
        Solicitud reclamo = factory.crearReclamo(Prioridad.ALTA);
        Solicitud tarea = factory.crearTarea(TipoServicio.ELECTRICIDAD);
        Solicitud incidente = factory.crearIncidente(NivelRiesgo.ALTO);
        System.out.println("\nSolicitud 1: " + reclamo.getClass().getSimpleName() + " - " + reclamo.getEstado().obtenerNombre());
        System.out.println("Solicitud 2: " + tarea.getClass().getSimpleName() + " - " + tarea.getEstado().obtenerNombre());
        System.out.println("Solicitud 3: " + incidente.getClass().getSimpleName() + " - " + incidente.getEstado().obtenerNombre());
    }
}
