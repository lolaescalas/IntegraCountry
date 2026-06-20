package tests;

import modelo.enums.NivelRiesgo;
import modelo.enums.Prioridad;
import modelo.enums.TipoServicio;
import modelo.abstractas.Solicitud;
import patrones.factory.SolicitudFactory;

public class FactoryMethodTest {

    public static void main(String[] args) {

        SolicitudFactory factory = new SolicitudFactory();

        Solicitud reclamo =
                factory.crearReclamo(Prioridad.ALTA);

        Solicitud tarea =
                factory.crearTarea(TipoServicio.ELECTRICIDAD);

        Solicitud incidente =
                factory.crearIncidente(NivelRiesgo.ALTO);

        System.out.println();
        System.out.println("Solicitud 1:");
        System.out.println("Tipo: " + reclamo.getClass().getSimpleName());
        System.out.println("Estado inicial: " + reclamo.getEstado().obtenerNombre());

        System.out.println();
        System.out.println("Solicitud 2:");
        System.out.println("Tipo: " + tarea.getClass().getSimpleName());
        System.out.println("Estado inicial: " + tarea.getEstado().obtenerNombre());


        System.out.println();
        System.out.println("Solicitud 3:");
        System.out.println("Tipo: " + incidente.getClass().getSimpleName());
        System.out.println("Estado inicial: " + incidente.getEstado().obtenerNombre());
        
    }
}