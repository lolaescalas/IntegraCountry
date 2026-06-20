package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.espacios.Lote;
import modelo.usuarios.Administrador;
import modelo.usuarios.Guardia;
import modelo.usuarios.Propietario;

/**
 * Guarda TODOS los usuarios del sistema en memoria.
 * Precarga las cuentas del sistema (admin y guardia) y algunos residentes de ejemplo.
 * Permite registrar nuevos residentes y autenticar credenciales.
 */
public class RepositorioUsuarios {

    private List<Usuario> usuarios = new ArrayList<>();

    public RepositorioUsuarios(Barrio barrio) {
        // ---- Cuentas del sistema (ya existen, no se registran) ----
        Administrador admin = new Administrador("Administrador General", "10000000", "admin@barrio.com", "1100000000");
        admin.setUsername("admin"); admin.setPassword("1234"); admin.setRol("Administrador");
        usuarios.add(admin);

        Guardia guardia = new Guardia("Guardia Principal", "20000000", "guardia@barrio.com", "1120000000");
        guardia.setUsername("guardia"); guardia.setPassword("1234"); guardia.setRol("Guardia");
        usuarios.add(guardia);

        // ---- Residentes de ejemplo (con login propio) ----
        List<Lote> lotes = barrio.getLotes();
        if (lotes.size() >= 2) {
            Propietario juan = new Propietario(1, lotes.get(0), "Juan Perez", "11222333", "juan@mail.com", "1111111111");
            juan.setUsername("juan"); juan.setPassword("1234"); juan.setRol("Residente");
            usuarios.add(juan);

            Propietario maria = new Propietario(2, lotes.get(1), "Maria Gomez", "44555666", "maria@mail.com", "1122222222");
            maria.setUsername("maria"); maria.setPassword("1234"); maria.setRol("Residente");
            usuarios.add(maria);
        }
    }

    public void agregar(Usuario u) { usuarios.add(u); }

    public List<Usuario> getUsuarios() { return usuarios; }

    // Devuelve solo los residentes (para la tabla de gestion de residentes)
    public List<Usuario> getResidentes() {
        List<Usuario> res = new ArrayList<>();
        for (Usuario u : usuarios)
            if ("Residente".equals(u.getRol())) res.add(u);
        return res;
    }

    // true si ese nombre de usuario ya esta tomado
    public boolean existeUsername(String username) {
        for (Usuario u : usuarios)
            if (username.equalsIgnoreCase(u.getUsername())) return true;
        return false;
    }

    // Devuelve el usuario si las credenciales coinciden, o null si no
    public Usuario autenticar(String username, String password) {
        for (Usuario u : usuarios)
            if (u.credencialesCoinciden(username, password)) return u;
        return null;
    }
}
