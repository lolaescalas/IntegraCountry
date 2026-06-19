import enums.NivelRiesgo;
import enums.Prioridad;
import enums.TipoServicio;
import fachada.AdministracionFacade;
import modelo.abstractas.Solicitud;
import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.usuarios.Residente;
import modelo.espacios.Lote;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== SISTEMA INTEGRACOUNTRY =====\n");

        // 1. Creamos el barrio y la fachada (puerta de entrada al sistema)
        Barrio barrio = new Barrio("Los Robles", "Av. Principal 123");
        AdministracionFacade administracion = new AdministracionFacade(barrio);

        // 2. CASO DE USO: Gestionar reclamo (muestra Factory + State + Observer + Strategy)
        System.out.println("--- Caso de uso: Gestionar reclamo ---");
        Solicitud reclamo = administracion.gestionarReclamo(Prioridad.ALTA);
        System.out.println("Estado actual del reclamo: " + reclamo.getEstado().obtenerNombre());
        System.out.println("Empleado asignado: " + reclamo.getEmpleado().getNombre());

        // Lo llevamos hasta Resuelta (segunda transicion del State)
        reclamo.getEstado().manejarSolicitud(reclamo);
        System.out.println("Estado final del reclamo: " + reclamo.getEstado().obtenerNombre());

        // 3. Gestionamos mas reclamos para ver el round-robin asignando distintos empleados
        System.out.println("\n--- Round-robin: varios reclamos, distintos empleados ---");
        Solicitud reclamo2 = administracion.gestionarReclamo(Prioridad.MEDIA);
        System.out.println("Reclamo 2 -> empleado: " + reclamo2.getEmpleado().getNombre());
        Solicitud reclamo3 = administracion.gestionarReclamo(Prioridad.BAJA);
        System.out.println("Reclamo 3 -> empleado: " + reclamo3.getEmpleado().getNombre());

        // 4. CASO DE USO: Registrar ingreso al barrio
        System.out.println("\n--- Caso de uso: Registrar ingreso ---");
        Lote lote = new Lote(101, "OCUPADO");
        Residente residente = new Residente(1, lote, "Sofia Diaz", "40111222",
                "sofia.diaz@mail.com", "1140111222");
        administracion.registrarResidente(residente);
        administracion.registrarIngreso("14:30", "INGRESO", residente, "Lote 101");

        System.out.println("\n===== FIN DE LA DEMOSTRACION =====");
    }
}