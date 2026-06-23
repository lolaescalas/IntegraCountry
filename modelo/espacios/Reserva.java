package modelo.espacios;
import interfaces.Cancelable;
import modelo.abstractas.Usuario;

public class Reserva implements Cancelable {

    private static int contador = 1;
    private int id;
    private String espacio;
    private String fecha;
    private String horario;
    private String estado; private Usuario solicitante;

    public Reserva(String espacio, String fecha, String horario, Usuario solicitante) {
        this.id = contador++;
        this.espacio = espacio;
        this.fecha = fecha;
        this.horario = horario;
        this.solicitante = solicitante;
        this.estado = "PENDIENTE";
    }

    public void confirmar() {
        this.estado = "CONFIRMADA"; }

    @Override public void cancelar() {
        this.estado = "CANCELADA"; }

    public int getId() {
        return id; }

    public String getEspacio() {
        return espacio; }

    public String getFecha() {
        return fecha; }

    public String getHorario() {
        return horario; }

    public String getEstado() {
        return estado; }

    public Usuario getSolicitante() {
        return solicitante; }

    public String getNombreSolicitante() {
        return solicitante != null ? solicitante.getNombre() : "-"; }
}
