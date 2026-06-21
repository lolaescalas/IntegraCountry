package patrones.facade;

import java.util.ArrayList;
import java.util.List;

import modelo.enums.NivelRiesgo;
import modelo.enums.Prioridad;
import modelo.abstractas.Solicitud;
import modelo.abstractas.Usuario;
import modelo.espacios.Autorizacion;
import modelo.espacios.Barrio;
import modelo.espacios.Ingreso;
import modelo.espacios.Lote;
import modelo.espacios.Reserva;
import modelo.expensas.Expensa;
import modelo.solicitudes.IncidenteSeguridad;
import modelo.solicitudes.Reclamo;
import modelo.usuarios.Inquilino;
import modelo.usuarios.Propietario;
import patrones.factory.SolicitudFactory;
import patrones.strategy.CanalInterno;
import patrones.strategy.Notificacion;
import repositorio.RepositorioAccesos;
import repositorio.RepositorioAutorizaciones;
import repositorio.RepositorioExpensas;
import repositorio.RepositorioReservas;
import repositorio.RepositorioSolicitudes;
import repositorio.RepositorioUsuarios;

public class AdministracionFacade {

    private SolicitudFactory solicitudFactory;
    private Barrio barrio;
    private RepositorioUsuarios repoUsuarios;
    private RepositorioSolicitudes repoSolicitudes;
    private RepositorioReservas repoReservas;
    private RepositorioExpensas repoExpensas;
    private RepositorioAccesos repoAccesos;
    private RepositorioAutorizaciones repoAutorizaciones;

    public AdministracionFacade(Barrio barrio) {
        this.barrio = barrio;
        this.solicitudFactory = new SolicitudFactory();
        this.repoUsuarios = new RepositorioUsuarios(barrio);
        this.repoSolicitudes = new RepositorioSolicitudes();
        this.repoReservas = new RepositorioReservas();
        this.repoExpensas = new RepositorioExpensas();
        this.repoAccesos = new RepositorioAccesos();
        this.repoAutorizaciones = new RepositorioAutorizaciones();
    }

    // ---------- AUTENTICACION ----------
    public Usuario autenticar(String username, String password) {
        return repoUsuarios.autenticar(username, password);
    }
    public boolean existeUsername(String username) {
        return repoUsuarios.existeUsername(username);
    }
    public Usuario registrarResidente(String nombre, String dni, int nroLote, String username, String password) {
        Lote lote = buscarOCrearLote(nroLote);
        int cod = repoUsuarios.getResidentes().size() + 1;
        Propietario nuevo = new Propietario(cod, lote, nombre, dni, "", "");
        nuevo.setUsername(username);
        nuevo.setPassword(password);
        nuevo.setRol("Residente");
        repoUsuarios.agregar(nuevo);
        System.out.println("[REGISTRO] Residente: " + nombre + " (usuario: " + username + ")");
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
        System.out.println("[SEGURIDAD] Incidente nivel " + nivel);
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

    // ---------- AUTORIZACION DE VISITAS (la crea el residente) ----------
    public Autorizacion autorizarVisita(String nombreVisita, String dniVisita, String tipo, Usuario residente) {
        Lote lote = loteDe(residente);
        Autorizacion a = new Autorizacion(nombreVisita, dniVisita, tipo, lote, residente);
        repoAutorizaciones.agregar(a);
        System.out.println("[AUTORIZACION] " + residente.getNombre() + " autorizo a " + nombreVisita + " (" + tipo + ")");
        return a;
    }
    public List<Autorizacion> getAutorizacionesDe(Usuario residente) {
        return repoAutorizaciones.getPorResidente(residente);
    }
    // Todas las autorizaciones vigentes (para que el guardia las vea)
    public List<Autorizacion> getAutorizacionesVigentes() {
        return repoAutorizaciones.getVigentes();
    }
    // Busca una autorizacion vigente por DNI -> el guardia autocompleta el lote
    public Autorizacion buscarAutorizacionPorDni(String dni) {
        return repoAutorizaciones.buscarVigentePorDni(dni);
    }

    // ---------- REGISTRO DE ACCESO CON VALIDACION (lo hace el guardia) ----------
    /**
     * Devuelve un mensaje de resultado. Si el acceso es valido, lo guarda.
     * Reglas:
     *  - RESIDENTE: el DNI debe existir como residente del sistema.
     *  - VISITA/PROVEEDOR/DELIVERY: debe haber una autorizacion vigente para ese DNI y lote.
     */
    public String registrarAcceso(String hora, String tipo, String movimiento, String nombre, String dni, String etiquetaLote) {
        int nroLote = numeroDeEtiqueta(etiquetaLote);

        if ("Residente".equals(tipo)) {
            Usuario res = buscarResidentePorDni(dni);
            if (res == null) {
                return "RECHAZADO: el DNI " + dni + " no corresponde a un residente registrado.";
            }
            if ("EGRESO".equals(movimiento) && !tieneIngresoPrevio(dni)) {
                return "RECHAZADO: no hay un ingreso previo registrado para el DNI " + dni + ".";
            }
            guardarAcceso(hora, tipo, movimiento, res.getNombre(), dni, etiquetaLote);
            return "OK: acceso de residente " + res.getNombre() + " registrado.";
        } else {
            // Visita / Proveedor / Delivery
            if ("EGRESO".equals(movimiento)) {
                // El egreso no requiere autorizacion, pero si un ingreso previo
                if (!tieneIngresoPrevio(dni)) {
                    return "RECHAZADO: no hay un ingreso previo registrado para el DNI " + dni + ".";
                }
                guardarAcceso(hora, tipo, movimiento, nombre, dni, etiquetaLote);
                return "OK: egreso de " + tipo + " registrado.";
            }
            Autorizacion auth = repoAutorizaciones.buscarVigente(dni, nroLote);
            if (auth == null) {
                return "RECHAZADO: no hay autorizacion vigente para el DNI " + dni + " en " + etiquetaLote + ".";
            }
            auth.marcarUsada();
            guardarAcceso(hora, tipo, movimiento, nombre, dni, etiquetaLote);
            return "OK: ingreso autorizado por " + auth.getNombreAutorizante() + ".";
        }
    }

    private void guardarAcceso(String hora, String tipo, String movimiento, String nombre, String dni, String destino) {
        Ingreso acceso = new Ingreso(hora, tipo, movimiento, nombre, dni, destino);
        repoAccesos.agregar(acceso);
        System.out.println("[ACCESO] " + movimiento + " - " + tipo + " " + nombre + " hacia " + destino);
    }

    public List<Ingreso> getAccesos() { return repoAccesos.getAccesos(); }

    // true si ese DNI tiene un INGRESO sin su EGRESO correspondiente (esta adentro)
    private boolean tieneIngresoPrevio(String dni) {
        String d = dni == null ? "" : dni.trim().replaceAll("\\s+", "");
        int balance = 0; // +1 ingreso, -1 egreso
        for (Ingreso i : repoAccesos.getAccesos()) {
            String id = i.getDniPersona() == null ? "" : i.getDniPersona().trim().replaceAll("\\s+", "");
            if (id.equals(d)) {
                if ("INGRESO".equals(i.getMovimiento())) balance++;
                else if ("EGRESO".equals(i.getMovimiento())) balance--;
            }
        }
        return balance > 0;
    }

    // ---------- RESERVAS ----------
    public Reserva solicitarReserva(String espacio, String fecha, String hora, Usuario solicitante) {
        Reserva r = new Reserva(espacio, fecha, hora, solicitante);
        repoReservas.agregar(r);
        System.out.println("[RESERVA] " + espacio + " -> PENDIENTE por " + r.getNombreSolicitante());
        return r;
    }

    // true si ese espacio ya esta tomado (PENDIENTE o CONFIRMADA) en esa fecha y hora
    public boolean hayConflictoReserva(String espacio, String fecha, String hora) {
        for (Reserva r : repoReservas.getReservas()) {
            if (!"CANCELADA".equals(r.getEstado())
                    && r.getEspacio().equals(espacio)
                    && r.getFecha().equals(fecha)
                    && r.getHorario().equals(hora)) {
                return true;
            }
        }
        return false;
    }
    public void aceptarReserva(Reserva r) { r.confirmar(); }
    public void rechazarReserva(Reserva r) { r.cancelar(); }
    public List<Reserva> getReservas() { return repoReservas.getReservas(); }

    // ---------- EXPENSAS ----------
    // Genera una expensa del periodo para cada lote del barrio (las que no existan)
    public int generarExpensasDelMes(String periodo, double montoBase) {
        int creadas = 0;
        for (Lote l : barrio.getLotes()) {
            boolean yaExiste = false;
            for (Expensa e : repoExpensas.getPorLote(l))
                if (e.getPeriodo().equals(periodo)) { yaExiste = true; break; }
            if (!yaExiste) {
                repoExpensas.agregar(new Expensa(l, periodo, montoBase));
                creadas++;
            }
        }
        System.out.println("[EXPENSAS] Generadas " + creadas + " expensas para " + periodo);
        return creadas;
    }

    public List<Expensa> getExpensas() { return repoExpensas.getExpensas(); }

    public List<Expensa> getExpensasDeResidente(Usuario residente) {
        Lote lote = loteDe(residente);
        return lote != null ? repoExpensas.getPorLote(lote) : new ArrayList<>();
    }

    public void pagarExpensa(Expensa e) { e.pagar(); }

        // ---------- BARRIO / HELPERS ----------
    public Barrio getBarrio() { return barrio; }
    public List<Lote> getLotes() { return barrio.getLotes(); }

    private Lote buscarOCrearLote(int nroLote) {
        for (Lote l : barrio.getLotes())
            if (l.getNumero() == nroLote) return l;
        Lote nuevo = new Lote(nroLote, "Activo");
        barrio.agregarLote(nuevo);
        return nuevo;
    }

    public void agregarMultaAExpensa(Expensa e, double monto) {
        e.agregarMulta(monto);
        System.out.println("[EXPENSAS] Multa de $" + (int) monto + " aplicada al lote " + e.getLote().getEtiqueta());
    }

    private Usuario buscarResidentePorDni(String dni) {
        for (Usuario u : repoUsuarios.getResidentes())
            if (dni.equals(u.getDni())) return u;
        return null;
    }

    private Lote loteDe(Usuario u) {
        if (u instanceof Propietario p) return p.getLote();
        if (u instanceof Inquilino i) return i.getLote();
        return null;
    }

    private int numeroDeEtiqueta(String etiqueta) {
        // "L-03" -> 3
        try { return Integer.parseInt(etiqueta.replaceAll("[^0-9]", "")); }
        catch (NumberFormatException e) { return -1; }
    }
}
