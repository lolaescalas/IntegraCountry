package tests;

import modelo.enums.Prioridad;
import modelo.solicitudes.Reclamo;
import repositorio.RepositorioEmpleados;
import servicio.AsignadorEmpleado;

public class StateTest {

    public static void main(String[] args) {

        AsignadorEmpleado asignador =
                new AsignadorEmpleado(new RepositorioEmpleados());

        Reclamo reclamo =
                new Reclamo(asignador, Prioridad.ALTA);

        System.out.println(". Se crea una solicitud: ");
        System.out.println("Estado: " + reclamo.getEstado().obtenerNombre());
        System.out.println("Empleado asignado a la solicitud: " + reclamo.getEmpleado());

        System.out.println();
        System.out.println(". Se intenta asignar un empleado a la solicitud:");
        System.out.println( reclamo.getEstado().asignarEmpleado(reclamo));

        System.out.println();
        System.out.println(". Avanza la solicitud:");
        System.out.println(reclamo.getEstado().avanzarSolicitud(reclamo));

        System.out.println("Estado: " + reclamo.getEstado().obtenerNombre());
        System.out.println("Empleado asignado a la solicitud: " + reclamo.getEmpleado().getNombre());

        System.out.println();
        System.out.println(". Avanza la solicitud:");
        System.out.println(reclamo.getEstado().avanzarSolicitud(reclamo));

        System.out.println("Estado: " + reclamo.getEstado().obtenerNombre());
        System.out.println("Empleado asignado a la solicitud: " + reclamo.getEmpleado().getNombre());


        System.out.println();
        System.out.println(". Se intenta avanzar la solicitud");
        System.out.println(reclamo.getEstado().avanzarSolicitud(reclamo));

        System.out.println();
        System.out.println(". Se libera al empleado");
        System.out.println( reclamo.getEstado().asignarEmpleado(reclamo));

        System.out.println("Empleado asignado a la solicitud: " + reclamo.getEmpleado());
    }
}