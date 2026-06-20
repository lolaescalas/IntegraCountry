package patrones.facade;

import modelo.enums.Prioridad;
import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.espacios.Ingreso;
import modelo.solicitudes.Reclamo;
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

    public Reclamo gestionarReclamo(String asunto, String descripcion, Prioridad prioridad) {
        Reclamo reclamo = (Reclamo) solicitudFactory.crearReclamo(prioridad);
        reclamo.setAsunto(asunto);
        reclamo.setDescripcion(descripcion);

        Notificacion notificacion = new Notificacion(new CanalInterno());
        reclamo.agregar(notificacion);
        reclamo.getEstado().avanzarSolicitud(reclamo);

        System.out.println("[SISTEMA] Nuevo reclamo registrado: " + asunto + " (" + prioridad + ")");
        return reclamo;
    }

    public Ingreso registrarIngreso(String hora, String tipo, Usuario persona, String destino) {
        Ingreso ingreso = new Ingreso(hora, tipo, persona, destino);
        System.out.println("[ACCESO] " + tipo + " registrado: "
                + persona.getNombre() + " hacia " + destino + " a las " + hora);
        return ingreso;
    }

    public void solicitarReserva(String espacio, String fecha, String hora) {
        System.out.println("[RESERVA] Solicitud de reserva: " + espacio + " en " + fecha + " a las " + hora);
    }

    public void registrarResidente(Usuario residente) {
        System.out.println("[REGISTRO] Residente registrado: " + residente.getNombre());
    }
}