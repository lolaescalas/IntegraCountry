package modelo.abstractas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.usuarios.Empleado;
import patrones.observer.IObservable;
import patrones.observer.IObservador;
import patrones.state.IEstadoSolicitud;
import patrones.state.EstadoPendiente;
import patrones.state.EstadoResuelta;
import servicio.AsignadorEmpleado;

public abstract class Solicitud implements IObservable {
    private static int contadorId = 1;
    private int id;
    private LocalDate fecha;
    private IEstadoSolicitud estado;
    private Empleado empleado;
    private Usuario solicitante;
    private AsignadorEmpleado asignador;
    private List<IObservador> observadores = new ArrayList<>();

    public Solicitud(AsignadorEmpleado asignador) {
        this.id = contadorId++;
        this.fecha = LocalDate.now();
        this.asignador = asignador;
        this.estado = new EstadoPendiente();
    }
    @Override public void agregar(IObservador o) {
        observadores.add(o); }

    @Override public void eliminar(IObservador o) { 
        observadores.remove(o); }

    @Override public void notificar() {
        String mensaje = "La solicitud #" + id + " cambio al estado: " + estado.obtenerNombre();
        for (IObservador o : observadores) o.actualizar(mensaje);
    }

    public int getId() {
        return id; }

    public LocalDate getFecha() {
        return fecha; }

    public IEstadoSolicitud getEstado() {
        return estado; }

    public void setEstado(IEstadoSolicitud estado) {
        this.estado = estado; notificar(); }

    public String getNombreEstado() {
        return estado.obtenerNombre(); }

    public boolean estaResuelta() {
        return estado instanceof EstadoResuelta; }

    public Empleado getEmpleado() {
        return empleado; }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado; }

    public Usuario getSolicitante() {
        return solicitante; }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante; }

    public String getNombreSolicitante() {
        return solicitante != null ? solicitante.getNombre() : "-"; }

    public AsignadorEmpleado getAsignador() {
        return asignador; }

    public abstract String getTipoDescripcion();
}
