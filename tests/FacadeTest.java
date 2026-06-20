package tests;
import modelo.enums.Prioridad;
import modelo.espacios.Barrio;
import modelo.espacios.Lote;
import modelo.solicitudes.Reclamo;
import modelo.abstractas.Usuario;
import patrones.facade.AdministracionFacade;

public class FacadeTest {
    public static void main(String[] args) {
        Barrio barrio = new Barrio("Los Aromos", "Av 1");
        barrio.agregarLote(new Lote(1, "Activo"));
        barrio.agregarLote(new Lote(2, "Activo"));
        AdministracionFacade admin = new AdministracionFacade(barrio);

        // Registrar residente con la API nueva (devuelve el Usuario creado)
        Usuario residente = admin.registrarResidente("Juan Perez", "30111222", 1, "juanp", "clave");
        System.out.println("\nCantidad residentes: " + admin.getResidentes().size());

        Reclamo reclamo = admin.gestionarReclamo("Falla luminaria",
                "La luz de la calle principal no funciona", Prioridad.ALTA, residente);
        System.out.println("Cantidad solicitudes: " + admin.getSolicitudes().size());

        admin.avanzarSolicitud(reclamo);
        System.out.println("Estado actual: " + reclamo.getEstado().obtenerNombre());

        admin.solicitarReserva("SUM", "20/12/2025", "21:00", residente);
        System.out.println("Cantidad reservas: " + admin.getReservas().size());

        // registrarAcceso con la API nueva (strings, valida)
        String r = admin.registrarAcceso("10:30", "Residente", "INGRESO", "Juan Perez", "30111222", "L-01");
        System.out.println("Acceso: " + r);
    }
}
