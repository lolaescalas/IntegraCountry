package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.abstractas.Usuario;
import modelo.espacios.Autorizacion;

public class RepositorioAutorizaciones {

    private List<Autorizacion> autorizaciones = new ArrayList<>();

    public void agregar(Autorizacion a) { autorizaciones.add(a); }
    public List<Autorizacion> getAutorizaciones() { return autorizaciones; }

    public List<Autorizacion> getPorResidente(Usuario residente) {
        List<Autorizacion> res = new ArrayList<>();
        for (Autorizacion a : autorizaciones)
            if (a.getAutorizadoPor() == residente) res.add(a);
        return res;
    }

    // Todas las autorizaciones que todavia no se usaron (para que el guardia las vea)
    public List<Autorizacion> getVigentes() {
        List<Autorizacion> res = new ArrayList<>();
        for (Autorizacion a : autorizaciones)
            if (!a.isUsada()) res.add(a);
        return res;
    }

    // Busca vigente por DNI y lote (compara DNI normalizado, sin espacios)
    public Autorizacion buscarVigente(String dni, int nroLote) {
        String d = normalizar(dni);
        for (Autorizacion a : autorizaciones) {
            if (!a.isUsada()
                    && normalizar(a.getDniVisita()).equals(d)
                    && a.getLote().getNumero() == nroLote) {
                return a;
            }
        }
        return null;
    }

    // Busca vigente solo por DNI (sin importar lote) -> sirve para autocompletar el lote
    public Autorizacion buscarVigentePorDni(String dni) {
        String d = normalizar(dni);
        for (Autorizacion a : autorizaciones)
            if (!a.isUsada() && normalizar(a.getDniVisita()).equals(d)) return a;
        return null;
    }

    private String normalizar(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", "");
    }
}
