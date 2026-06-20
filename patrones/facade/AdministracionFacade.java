package patrones.facade;

import java.util.ArrayList;
import java.util.List;

import modelo.enums.NivelRiesgo;
import modelo.enums.Prioridad;
import modelo.abstractas.Solicitud;
import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.espacios.Ingreso;
import modelo.espacios.Lote;
import modelo.espacios.Reserva;
import modelo.solicitudes.IncidenteSeguridad;
import modelo.solicitudes.Reclamo;
import patrones.factory.SolicitudFactory;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;
import repositorio.RepositorioReservas;
import repositorio.RepositorioResidentes;
import repositorio.RepositorioSolicitudes;

public class AdministracionFacade {

    private SolicitudFactory solicitudFactory;
    private Barrio barrio;
    private RepositorioResidentes repoResidentes;
    private RepositorioSolicitudes repoSolicitudes;
    private RepositorioReservas repoReservas;

    public AdministracionFacade(Barrio barrio) {
        this.barrio = barrio;
        this.solicitudFactory = new SolicitudFactory();
        this.repoResidentes = new RepositorioResidentes(barrio);
        this.repoSolicitudes = new RepositorioSolicitudes();
        this.repoReservas = new RepositorioReservas();
    }

    // ---------- RECLAMOS ----------
    public Reclamo gestionarReclamo(String asunto, String descripcion, Prioridad prioridad, Usuario solicitante) {
        Reclamo reclamo = (Reclamo) solicitudFactory.crearReclamo(prioridad);
        reclamo.setAsunto(asunto);
        reclamo.setDescripcion(descripcion);
        reclamo.setSolicitante(solicitante);
        Notificacion notificacion = new Notificacion(new CanalInterno());
        reclamo.agregar(notificacion);
        repoSolicitudes.agregar(reclamo);
        System.out.println("[SISTEMA] Nuevo reclamo: " + asunto + " (" + prioridad + ") por " + reclamo.getNombreSolicitante());
        return reclamo;
    }

    // ---------- INCIDENTES ----------
    public IncidenteSeguridad reportarIncidente(String descripcion, NivelRiesgo nivel, Usuario solicitante) {
        IncidenteSeguridad inc = (IncidenteSeguridad) solicitudFactory.crearIncidente(nivel);
        inc.setDescripcion(descripcion);
        inc.setSolicitante(solicitante);
        Notificacion notificacion = new Notificacion(new CanalInterno());
        inc.agregar(notificacion);
        repoSolicitudes.agregar(inc);
        System.out.println("[SEGURIDAD] Incidente nivel " + nivel);
        return inc;
    }

    public void avanzarSolicitud(Solicitud s) { s.getEstado().avanzarSolicitud(s); }

    public List<Solicitud> getSolicitudes() { return repoSolicitudes.getSolicitudes(); }

    // Solo las que NO estan resueltas (las resueltas quedan en el repo, fuera de la vista)
    public List<Solicitud> getSolicitudesActivas() {
        List<Solicitud> activas = new ArrayList<>();
        for (Solicitud s : repoSolicitudes.getSolicitudes())
            if (!s.estaResuelta()) activas.add(s);
        return activas;
    }

    // ---------- RESIDENTES ----------
    public void registrarResidente(Usuario residente) {
        repoResidentes.agregar(residente);
        System.out.println("[REGISTRO] Residente: " + residente.getNombre());
    }
    public List<Usuario> getResidentes() { return repoResidentes.getResidentes(); }

    // ---------- ACCESOS (ingreso y egreso) ----------
    public Ingreso registrarAcceso(String hora, String tipo, String movimiento, Usuario persona, String destino) {
        Ingreso acceso = new Ingreso(hora, tipo, movimiento, persona, destino);
        System.out.println("[ACCESO] " + movimiento + " - " + tipo + " hacia " + destino + " a las " + hora);
        return acceso;
    }

    // ---------- RESERVAS ----------
    public Reserva solicitarReserva(String espacio, String fecha, String hora, Usuario solicitante) {
        Reserva r = new Reserva(espacio, fecha, hora, solicitante);
        repoReservas.agregar(r);
        System.out.println("[RESERVA] " + espacio + " " + fecha + " " + hora + " -> PENDIENTE");
        return r;
    }
    public void aceptarReserva(Reserva r) { r.confirmar(); System.out.println("[RESERVA] #" + r.getId() + " CONFIRMADA"); }
    public void rechazarReserva(Reserva r) { r.cancelar(); System.out.println("[RESERVA] #" + r.getId() + " CANCELADA"); }
    public List<Reserva> getReservas() { return repoReservas.getReservas(); }

    // ---------- BARRIO / LOTES ----------
    public Barrio getBarrio() { return barrio; }
    public List<Lote> getLotes() { return barrio.getLotes(); }
}
