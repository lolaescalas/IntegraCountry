package tests;

import modelo.enums.Prioridad;
import modelo.solicitudes.Reclamo;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;
import repositorio.RepositorioEmpleados;
import servicio.AsignadorEmpleado;

public class ObserverTest {

    public static void main(String[] args) {

        AsignadorEmpleado asignador =
                new AsignadorEmpleado(new RepositorioEmpleados());

        Reclamo reclamo =
                new Reclamo(asignador, Prioridad.ALTA);

        Notificacion notificacion =
                new Notificacion(new CanalInterno());

        // Suscribir observador
        reclamo.agregar(notificacion);

        System.out.println("Estado inicial: "
                + reclamo.getEstado().obtenerNombre());

        System.out.println();
        System.out.println("Avanzando solicitud...");

        // Pendiente -> En curso
        reclamo.getEstado().avanzarSolicitud(reclamo);

        System.out.println();
        System.out.println("Ultima notificacion recibida:");
        System.out.println(notificacion.getMensaje());

        System.out.println();
        System.out.println("Avanzando nuevamente...");

        // En curso -> Resuelta
        reclamo.getEstado().avanzarSolicitud(reclamo);

        System.out.println();
        System.out.println("Ultima notificacion recibida:");
        System.out.println(notificacion.getMensaje());

        System.out.println();
        System.out.println("Fecha notificacion:");
        System.out.println(notificacion.getFecha());

    }
}