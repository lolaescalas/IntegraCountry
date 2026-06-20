package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.abstractas.Usuario;
import modelo.espacios.Lote;
import modelo.usuarios.Inquilino;
import modelo.usuarios.Propietario;

public class RepositorioResidentes {

    private List<Usuario> residentes = new ArrayList<>();

    public RepositorioResidentes() {
        // Datos iniciales de ejemplo (ya no estan en la vista, estan en el repositorio)
        residentes.add(new Propietario(1, new Lote(1, "Activo"), "Juan Perez", "11222333", "juan@mail.com", "1111111111"));
        residentes.add(new Inquilino(2, new Lote(2, "Activo"), "Maria Gomez", "44555666", "maria@mail.com", "1122222222"));
        residentes.add(new Propietario(3, new Lote(3, "Moroso"), "Carlos Lopez", "77888999", "carlos@mail.com", "1133333333"));
    }

    public void agregar(Usuario residente) { residentes.add(residente); }
    public List<Usuario> getResidentes() { return residentes; }
}
