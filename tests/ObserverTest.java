package tests;
import modelo.enums.Prioridad;
import modelo.solicitudes.Reclamo;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;
import repositorio.RepositorioEmpleados;
import servicio.AsignadorEmpleado;
public class ObserverTest {
    public static void main(String[] args) {
        AsignadorEmpleado asignador = new AsignadorEmpleado(new RepositorioEmpleados());
        Reclamo reclamo = new Reclamo(asignador, Prioridad.ALTA);
        Notificacion notificacion = new Notificacion(new CanalInterno());
        reclamo.agregar(notificacion);
        System.out.println("Estado inicial: " + reclamo.getEstado().obtenerNombre());
        reclamo.getEstado().avanzarSolicitud(reclamo);
        System.out.println("Notificacion: " + notificacion.getMensaje());
        reclamo.getEstado().avanzarSolicitud(reclamo);
        System.out.println("Notificacion: " + notificacion.getMensaje());
        System.out.println("Fecha: " + notificacion.getFecha());
    }
}
