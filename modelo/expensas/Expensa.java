package modelo.expensas;

import modelo.enums.EstadoExpensa;
import modelo.espacios.Lote;

/**
 * Expensa de un lote para un periodo. Calcula su total (base + multas) y maneja su estado.
 */
public class Expensa {

    private static int contador = 1;

    private int id;
    private Lote lote;
    private String periodo;      // ej "05/2026"
    private double montoBase;
    private double multas;
    private EstadoExpensa estado;

    public Expensa(Lote lote, String periodo, double montoBase) {
        this.id = contador++;
        this.lote = lote;
        this.periodo = periodo;
        this.montoBase = montoBase;
        this.multas = 0;
        this.estado = EstadoExpensa.PENDIENTE;
    }

    // El total se CALCULA, no se guarda suelto (Information Expert: la expensa sabe su total)
    public double getTotal() { return montoBase + multas; }

    public void agregarMulta(double monto) { this.multas += monto; }

    public void pagar() { this.estado = EstadoExpensa.PAGA; }

    public void marcarVencida() {
        if (estado == EstadoExpensa.PENDIENTE) this.estado = EstadoExpensa.VENCIDA;
    }

    public int getId() { return id; }
    public Lote getLote() { return lote; }
    public String getPeriodo() { return periodo; }
    public double getMontoBase() { return montoBase; }
    public double getMultas() { return multas; }
    public EstadoExpensa getEstado() { return estado; }
}
