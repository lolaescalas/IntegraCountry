package tests;
import modelo.enums.Prioridad;
import modelo.solicitudes.Reclamo;
import repositorio.RepositorioEmpleados;
import servicio.AsignadorEmpleado;

public class StateTest {

    public static void main(String[] args) {

        AsignadorEmpleado asignador = new AsignadorEmpleado(new RepositorioEmpleados());
        Reclamo reclamo = new Reclamo(asignador, Prioridad.ALTA);


        System.out.println("Estado: " + reclamo.getEstado().obtenerNombre());
        System.out.println(reclamo.getEstado().asignarEmpleado(reclamo));
        System.out.println(reclamo.getEstado().avanzarSolicitud(reclamo));
        System.out.println("Estado: " + reclamo.getEstado().obtenerNombre() + " - Empleado: " + reclamo.getEmpleado().getNombre());
        System.out.println(reclamo.getEstado().avanzarSolicitud(reclamo));
        System.out.println("Estado: " + reclamo.getEstado().obtenerNombre());
        
    }
}
