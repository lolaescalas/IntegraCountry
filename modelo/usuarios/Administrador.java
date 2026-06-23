package modelo.usuarios;
import modelo.abstractas.Usuario;

public class Administrador extends Usuario {

    public Administrador(String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono); }
}
