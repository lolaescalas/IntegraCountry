package vistas;

import modelo.abstractas.Usuario;

/**
 * Guarda quien inicio sesion. Asi las solicitudes saben QUIEN las crea.
 */
public class Sesion {
    private final String rol;
    private final Usuario usuario;   // puede ser null para Admin/Guardia

    public Sesion(String rol, Usuario usuario) {
        this.rol = rol;
        this.usuario = usuario;
    }
    public String getRol() { return rol; }
    public Usuario getUsuario() { return usuario; }
    public String getNombre() { return usuario != null ? usuario.getNombre() : rol; }
}
