package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.espacios.Ingreso;

public class RepositorioAccesos {
    private List<Ingreso> accesos = new ArrayList<>();
    public void agregar(Ingreso i) { accesos.add(i); }
    public List<Ingreso> getAccesos() { return accesos; }
}
