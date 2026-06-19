package modelo.espacios;

import java.time.LocalDate;

import modelo.abstractas.Usuario;

public class Ingreso {

    private LocalDate fecha;
    private String hora;
    private String tipo;
    private Usuario persona;
    private String destino;

    public Ingreso(String hora, String tipo, Usuario persona, String destino) {
        this.fecha = LocalDate.now();
        this.hora = hora;
        this.tipo = tipo;
        this.persona = persona;
        this.destino = destino;
    }

    public LocalDate getFecha() { 
        return fecha; }
        
    public String getHora() {
         return hora; }

    public String getTipo() {
         return tipo; }

    public Usuario getPersona() {
         return persona; }
         
    public String getDestino() {
         return destino; }
}