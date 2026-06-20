package modelo.espacios;

import modelo.abstractas.Usuario;

/**
 * Una visita/proveedor/delivery autorizada por un residente para ingresar a su lote.
 */
public class Autorizacion {

    private static int contador = 1;

    private int id;
    private String nombreVisita;
    private String dniVisita;
    private String tipo;          // Visita, Proveedor, Delivery
    private Lote lote;            // a que lote puede ingresar
    private Usuario autorizadoPor; // residente que la autorizo
    private boolean usada;        // true cuando ya ingreso

    public Autorizacion(String nombreVisita, String dniVisita, String tipo, Lote lote, Usuario autorizadoPor) {
        this.id = contador++;
        this.nombreVisita = nombreVisita;
        this.dniVisita = dniVisita;
        this.tipo = tipo;
        this.lote = lote;
        this.autorizadoPor = autorizadoPor;
        this.usada = false;
    }

    public int getId() { return id; }
    public String getNombreVisita() { return nombreVisita; }
    public String getDniVisita() { return dniVisita; }
    public String getTipo() { return tipo; }
    public Lote getLote() { return lote; }
    public Usuario getAutorizadoPor() { return autorizadoPor; }
    public String getNombreAutorizante() { return autorizadoPor != null ? autorizadoPor.getNombre() : "-"; }
    public boolean isUsada() { return usada; }
    public void marcarUsada() { this.usada = true; }
}
