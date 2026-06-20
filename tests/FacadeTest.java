package tests;

import modelo.enums.Prioridad;
import modelo.espacios.Barrio;
import modelo.solicitudes.Reclamo;
import patrones.facade.AdministracionFacade;
import modelo.abstractas.Usuario;

public class FacadeTest {

    public static void main(String[] args) {


        Barrio barrio = new Barrio("Los Aromos");

        AdministracionFacade admin =
                new AdministracionFacade(barrio);

        Usuario residente = new Usuario(
                "Juan Perez",
                "30111222",
                "juan@gmail.com",
                "1130011222"
        ) {};

        admin.registrarResidente(residente);

        System.out.println();
        System.out.println("Cantidad residentes: "
                + admin.getResidentes().size());

        Reclamo reclamo = admin.gestionarReclamo(
                "Falla luminaria",
                "La luz de la calle principal no funciona",
                Prioridad.ALTA,
                residente
        );

        System.out.println();
        System.out.println("Cantidad solicitudes: "
                + admin.getSolicitudes().size());

        // Avanzar reclamo
        admin.avanzarSolicitud(reclamo);

        System.out.println(
                "Estado actual: "
                + reclamo.getEstado().obtenerNombre());

        admin.solicitarReserva(
                "SUM",
                "20/12/2025",
                "21:00",
                residente
        );

        System.out.println();
        System.out.println("Cantidad reservas: " + admin.getReservas().size());

        admin.registrarAcceso(
                "10:30",
                "INVITADO",
                "INGRESO",
                residente,
                "Lote 15"
        );
    }
}