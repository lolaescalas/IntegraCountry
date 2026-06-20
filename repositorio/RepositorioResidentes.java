package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.abstractas.Usuario;
import modelo.espacios.Barrio;
import modelo.espacios.Lote;
import modelo.usuarios.Inquilino;
import modelo.usuarios.Propietario;

public class RepositorioResidentes {

    private List<Usuario> residentes = new ArrayList<>();

    public RepositorioResidentes(Barrio barrio) {
        // Toma los lotes que ya existen en el barrio (no crea lotes sueltos)
        List<Lote> lotes = barrio.getLotes();
        if (lotes.size() >= 3) {
            residentes.add(new Propietario(1, lotes.get(0), "Juan Perez", "11222333", "juan@mail.com", "1111111111"));
            residentes.add(new Inquilino(2, lotes.get(1), "Maria Gomez", "44555666", "maria@mail.com", "1122222222"));
            residentes.add(new Propietario(3, lotes.get(2), "Carlos Lopez", "77888999", "carlos@mail.com", "1133333333"));
        }
    }

    public void agregar(Usuario residente) { residentes.add(residente); }
    public List<Usuario> getResidentes() { return residentes; }
}
