import modelo.enums.Prioridad;
import fachada.AdministracionFacade;
import modelo.abstractas.Solicitud;
import modelo.espacios.Barrio;
import modelo.espacios.Lote;
import modelo.usuarios.Residente;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== SISTEMA INTEGRACOUNTRY =====\n");

        Barrio barrio = new Barrio("Los Robles", "Av. Principal 123");
        AdministracionFacade administracion = new AdministracionFacade(barrio);

        System.out.println("--- Caso de uso: Gestionar reclamo ---");
        Solicitud reclamo = administracion.gestionarReclamo(Prioridad.ALTA);
        System.out.println("Estado actual: " + reclamo.getEstado().obtenerNombre());
        System.out.println("Empleado asignado: " + reclamo.getEmpleado().getNombre());

        reclamo.getEstado().avanzarSolicitud(reclamo);
        System.out.println("Estado final: " + reclamo.getEstado().obtenerNombre());

        System.out.println("\n--- Round-robin: varios reclamos ---");
        Solicitud r2 = administracion.gestionarReclamo(Prioridad.MEDIA);
        System.out.println("Reclamo 2 -> empleado: " + r2.getEmpleado().getNombre());
        Solicitud r3 = administracion.gestionarReclamo(Prioridad.BAJA);
        System.out.println("Reclamo 3 -> empleado: " + r3.getEmpleado().getNombre());

        System.out.println("\n--- Caso de uso: Registrar ingreso ---");
        Lote lote = new Lote(101, "OCUPADO");
        Residente residente = new Residente(1, lote, "Sofia Diaz", "40111222",
                "sofia.diaz@mail.com", "1140111222");
        administracion.registrarResidente(residente);
        administracion.registrarIngreso("14:30", "INGRESO", residente, "Lote 101");

        System.out.println("\n===== FIN =====");
    }
}