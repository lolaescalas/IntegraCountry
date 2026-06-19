package patrones.facade;

import modelo.enums.Prioridad;
import modelo.abstractas.Solicitud;
import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.espacios.Ingreso;
import patrones.factory.SolicitudFactory;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;

public class AdministracionFacade {

    private SolicitudFactory solicitudFactory;
    private Barrio barrio;

    public AdministracionFacade(Barrio barrio) {
        this.barrio = barrio;
        this.solicitudFactory = new SolicitudFactory();
    }

    public Solicitud gestionarReclamo(Prioridad prioridad) {
        Solicitud reclamo = solicitudFactory.crearReclamo(prioridad);

        Notificacion notificacion = new Notificacion(new CanalInterno());
        reclamo.agregar(notificacion);

        reclamo.getEstado().manejarSolicitud(reclamo);

        return reclamo;
    }

    public Ingreso registrarIngreso(String hora, String tipo, Usuario persona, String destino) {
        Ingreso ingreso = new Ingreso(hora, tipo, persona, destino);
        System.out.println("[ACCESO] " + tipo + " registrado: "
                + persona.getNombre() + " hacia " + destino + " a las " + hora);
        return ingreso;
    }

    public void registrarResidente(Usuario residente) {
        System.out.println("[REGISTRO] Residente registrado: " + residente.getNombre());
    }
}