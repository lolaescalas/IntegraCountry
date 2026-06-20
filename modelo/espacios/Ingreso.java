package modelo.espacios;

import java.time.LocalDate;
import modelo.abstractas.Usuario;

public class Ingreso {
    private LocalDate fecha;
    private String hora;
    private String tipo;          // Residente, Visita, Proveedor, Delivery
    private String movimiento;    // INGRESO / EGRESO
    private Usuario persona;       // puede ser null (visitas no son usuarios del sistema)
    private String nombrePersona;
    private String dniPersona;
    private String destino;

    // Constructor original (compatibilidad con codigo que pasa un Usuario)
    public Ingreso(String hora, String tipo, String movimiento, Usuario persona, String destino) {
        this.fecha = LocalDate.now();
        this.hora = hora;
        this.tipo = tipo;
        this.movimiento = movimiento;
        this.persona = persona;
        this.nombrePersona = (persona != null) ? persona.getNombre() : "";
        this.dniPersona = (persona != null) ? persona.getDni() : "";
        this.destino = destino;
    }

    // Constructor nuevo: nombre y dni como texto (para visitas sin usuario)
    public Ingreso(String hora, String tipo, String movimiento, String nombrePersona, String dniPersona, String destino) {
        this.fecha = LocalDate.now();
        this.hora = hora;
        this.tipo = tipo;
        this.movimiento = movimiento;
        this.persona = null;
        this.nombrePersona = nombrePersona;
        this.dniPersona = dniPersona;
        this.destino = destino;
    }

    public LocalDate getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getTipo() { return tipo; }
    public String getMovimiento() { return movimiento; }
    public Usuario getPersona() { return persona; }
    public String getNombrePersona() { return nombrePersona; }
    public String getDniPersona() { return dniPersona; }
    public String getDestino() { return destino; }
}
