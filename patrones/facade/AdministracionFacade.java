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
import modelo.usuarios.Propietario;
import patrones.factory.SolicitudFactory;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;
import repositorio.RepositorioReservas;
import repositorio.RepositorioSolicitudes;
import repositorio.RepositorioUsuarios;

public class AdministracionFacade {

    private SolicitudFactory solicitudFactory;
    private Barrio barrio;
    private RepositorioUsuarios repoUsuarios;
    private RepositorioSolicitudes repoSolicitudes;
    private RepositorioReservas repoReservas;

    public AdministracionFacade(Barrio barrio) {
        this.barrio = barrio;
        this.solicitudFactory = new SolicitudFactory();
        this.repoUsuarios = new RepositorioUsuarios(barrio);
        this.repoSolicitudes = new RepositorioSolicitudes();
        this.repoReservas = new RepositorioReservas();
    }

    // ---------- AUTENTICACION ----------
    public Usuario autenticar(String username, String password) {
        return repoUsuarios.autenticar(username, password);
    }

    public boolean existeUsername(String username) {
        return repoUsuarios.existeUsername(username);
    }

    // Registra un nuevo residente con sus credenciales. Devuelve el residente creado.
    public Usuario registrarResidente(String nombre, String dni, int nroLote, String username, String password) {
        // Reusa el lote si existe en el barrio, si no lo crea y lo agrega
        Lote lote = null;
        for (Lote l : barrio.getLotes())
            if (l.getNumero() == nroLote) { lote = l; break; }
        if (lote == null) {
            lote = new Lote(nroLote, "Activo");
            barrio.agregarLote(lote);
        }

        int cod = repoUsuarios.getResidentes().size() + 1;
        Propietario nuevo = new Propietario(cod, lote, nombre, dni, "", "");
        nuevo.setUsername(username);
        nuevo.setPassword(password);
        nuevo.setRol("Residente");
        repoUsuarios.agregar(nuevo);
        System.out.println("[REGISTRO] Nuevo residente: " + nombre + " (usuario: " + username + ")");
        return nuevo;
    }

    public List<Usuario> getResidentes() { return repoUsuarios.getResidentes(); }

    // ---------- RECLAMOS ----------
    public Reclamo gestionarReclamo(String asunto, String descripcion, Prioridad prioridad, Usuario solicitante) {
        Reclamo reclamo = (Reclamo) solicitudFactory.crearReclamo(prioridad);
        reclamo.setAsunto(asunto);
        reclamo.setDescripcion(descripcion);
        reclamo.setSolicitante(solicitante);
        Notificacion notificacion = new Notificacion(new CanalInterno());
        reclamo.agregar(notificacion);
        repoSolicitudes.agregar(reclamo);
        System.out.println("[SISTEMA] Reclamo: " + asunto + " por " + reclamo.getNombreSolicitante());
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
        System.out.println("[SEGURIDAD] Incidente nivel " + nivel + " por " + inc.getNombreSolicitante());
        return inc;
    }

    public void avanzarSolicitud(Solicitud s) { s.getEstado().avanzarSolicitud(s); }
    public List<Solicitud> getSolicitudes() { return repoSolicitudes.getSolicitudes(); }
    public List<Solicitud> getSolicitudesActivas() {
        List<Solicitud> activas = new ArrayList<>();
        for (Solicitud s : repoSolicitudes.getSolicitudes())
            if (!s.estaResuelta()) activas.add(s);
        return activas;
    }

    // ---------- ACCESOS ----------
    public Ingreso registrarAcceso(String hora, String tipo, String movimiento, Usuario persona, String destino) {
        Ingreso acceso = new Ingreso(hora, tipo, movimiento, persona, destino);
        System.out.println("[ACCESO] " + movimiento + " - " + tipo + " hacia " + destino + " a las " + hora);
        return acceso;
    }

    // ---------- RESERVAS ----------
    public Reserva solicitarReserva(String espacio, String fecha, String hora, Usuario solicitante) {
        Reserva r = new Reserva(espacio, fecha, hora, solicitante);
        repoReservas.agregar(r);
        System.out.println("[RESERVA] " + espacio + " " + fecha + " -> PENDIENTE por " + r.getNombreSolicitante());
        return r;
    }
    public void aceptarReserva(Reserva r) { r.confirmar(); System.out.println("[RESERVA] #" + r.getId() + " CONFIRMADA"); }
    public void rechazarReserva(Reserva r) { r.cancelar(); System.out.println("[RESERVA] #" + r.getId() + " CANCELADA"); }
    public List<Reserva> getReservas() { return repoReservas.getReservas(); }

    // ---------- BARRIO ----------
    public Barrio getBarrio() { return barrio; }
    public List<Lote> getLotes() { return barrio.getLotes(); }
}
