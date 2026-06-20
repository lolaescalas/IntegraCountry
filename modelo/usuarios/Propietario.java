package modelo.usuarios;
import modelo.abstractas.Usuario;
import modelo.espacios.Lote;

public class Propietario extends Usuario {
    private int codigoPropietario;
    private Lote lote;
    public Propietario(int codigoPropietario, Lote lote, String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono); this.codigoPropietario = codigoPropietario; this.lote = lote;
    }
    public int getCodigoPropietario() { return codigoPropietario; }
    public Lote getLote() { return lote; }
}
