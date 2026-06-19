package modelo.usuarios;

import modelo.abstractas.Usuario;
import modelo.espacios.Lote;

public class Propietario extends Usuario {

    private int codigoPropietario;
    private Lote lote;

    public Propietario(String nombre, String dni, String email, String telefono, int codigoPropietario, Lote lote) {
        super(nombre, dni, email, telefono);
        this.codigoPropietario = codigoPropietario;
        this.lote = lote;
    }
    public int getCodigoPropietario() {
        return codigoPropietario;
    }
    public void setCodigoPropietario(int codigoPropietario) {
        this.codigoPropietario = codigoPropietario;
    }
    public Lote getLote() {
        return lote;
    }
    public void setLote(Lote lote) {
        this.lote = lote;
    }

    
}
