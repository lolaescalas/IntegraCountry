package modelo.espacios;

import java.time.LocalDate;
import modelo.abstractas.Usuario;

public class Ingreso {
    private LocalDate fecha;
    private String hora;
    private String tipo;        // Visita, Proveedor, Delivery
    private String movimiento;  // INGRESO / EGRESO
    private Usuario persona;
    private String destino;

    public Ingreso(String hora, String tipo, String movimiento, Usuario persona, String destino) {
        this.fecha = LocalDate.now();
        this.hora = hora;
        this.tipo = tipo;
        this.movimiento = movimiento;
        this.persona = persona;
        this.destino = destino;
    }
    public LocalDate getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getTipo() { return tipo; }
    public String getMovimiento() { return movimiento; }
    public Usuario getPersona() { return persona; }
    public String getDestino() { return destino; }
}
