package vistas;

import modelo.abstractas.Usuario;

/**
 * Guarda el usuario que inicio sesion. El rol sale del propio usuario.
 */
public class Sesion {
    private final Usuario usuario;

    public Sesion(Usuario usuario) { this.usuario = usuario; }

    public Usuario getUsuario() { return usuario; }
    public String getRol() { return usuario.getRol(); }
    public String getNombre() { return usuario.getNombre(); }
}
