package patrones.facade;

import java.util.List;

import modelo.enums.NivelRiesgo;
import modelo.enums.Prioridad;
import modelo.abstractas.Solicitud;
import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.espacios.Ingreso;
import modelo.solicitudes.IncidenteSeguridad;
import modelo.solicitudes.Reclamo;
import patrones.factory.SolicitudFactory;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;
import repositorio.RepositorioResidentes;
import repositorio.RepositorioSolicitudes;

public class AdministracionFacade {

    private SolicitudFactory solicitudFactory;
    private Barrio barrio;
    private RepositorioResidentes repoResidentes;
    private RepositorioSolicitudes repoSolicitudes;

    public AdministracionFacade(Barrio barrio) {
        this.barrio = barrio;
        this.solicitudFactory = new SolicitudFactory();
        this.repoResidentes = new RepositorioResidentes();
        this.repoSolicitudes = new RepositorioSolicitudes();
    }

    // ---------- RECLAMOS ----------
    public Reclamo gestionarReclamo(String asunto, String descripcion, Prioridad prioridad) {
        Reclamo reclamo = (Reclamo) solicitudFactory.crearReclamo(prioridad);
        reclamo.setAsunto(asunto);
        reclamo.setDescripcion(descripcion);

        Notificacion notificacion = new Notificacion(new CanalInterno());
        reclamo.agregar(notificacion);

        repoSolicitudes.agregar(reclamo);
        System.out.println("[SISTEMA] Nuevo reclamo registrado: " + asunto + " (" + prioridad + ")");
        return reclamo;
    }

    // ---------- INCIDENTES ----------
    public IncidenteSeguridad reportarIncidente(String descripcion, NivelRiesgo nivel) {
        IncidenteSeguridad inc = (IncidenteSeguridad) solicitudFactory.crearIncidente(nivel);
        inc.setDescripcion(descripcion);
        Notificacion notificacion = new Notificacion(new CanalInterno());
        inc.agregar(notificacion);
        repoSolicitudes.agregar(inc);
        System.out.println("[SEGURIDAD] Incidente reportado nivel " + nivel);
        return inc;
    }

    // Avanza el estado de una solicitud (dispara Observer automaticamente)
    public void avanzarSolicitud(Solicitud s) {
        s.getEstado().avanzarSolicitud(s);
    }

    public List<Solicitud> getSolicitudes() { return repoSolicitudes.getSolicitudes(); }

    // ---------- RESIDENTES ----------
    public void registrarResidente(Usuario residente) {
        repoResidentes.agregar(residente);
        System.out.println("[REGISTRO] Residente registrado: " + residente.getNombre());
    }

    public List<Usuario> getResidentes() { return repoResidentes.getResidentes(); }

    // ---------- ACCESOS ----------
    public Ingreso registrarIngreso(String hora, String tipo, Usuario persona, String destino) {
        Ingreso ingreso = new Ingreso(hora, tipo, persona, destino);
        System.out.println("[ACCESO] " + tipo + " registrado hacia " + destino + " a las " + hora);
        return ingreso;
    }

    public void solicitarReserva(String espacio, String fecha, String hora) {
        System.out.println("[RESERVA] " + espacio + " en " + fecha + " a las " + hora);
    }

    public Barrio getBarrio() { return barrio; }
}
