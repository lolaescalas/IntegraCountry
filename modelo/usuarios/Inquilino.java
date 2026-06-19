package modelo.usuarios;

import modelo.abstractas.Usuario;
import modelo.espacios.Lote;

public class Inquilino extends Usuario {

    private int codigoInquilino;
    private Lote lote;

        public Inquilino(int codigoInquilino, Lote lote, String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono);
        this.codigoInquilino = codigoInquilino;
        this.lote = lote;
    }

    public int codigoInquilino() { return codigoInquilino; }
    public Lote getLote() { return lote; }

}
