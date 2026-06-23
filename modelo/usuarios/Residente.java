package modelo.usuarios;
import modelo.abstractas.Usuario;
import modelo.espacios.Lote;

public class Residente extends Usuario {

    private int codigoResidente;
    private Lote lote;

    public Residente(int codigoResidente, Lote lote, String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono);
        this.codigoResidente = codigoResidente;
        this.lote = lote;
    }

    public int getCodigoResidente() {
        return codigoResidente; }

    public Lote getLote() {
        return lote; }
}
