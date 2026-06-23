package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.abstractas.Usuario;
import modelo.espacios.Autorizacion;

public class RepositorioAutorizaciones {

    private List<Autorizacion> autorizaciones = new ArrayList<>();

    public void agregar(Autorizacion a) {
        autorizaciones.add(a); }

    public List<Autorizacion> getAutorizaciones() {
        return autorizaciones; }


    public List<Autorizacion> getPorResidente(Usuario residente) {
        List<Autorizacion> res = new ArrayList<>();
        for (Autorizacion a : autorizaciones)
            if (a.getAutorizadoPor() == residente) res.add(a);
        return res;
    }

    public List<Autorizacion> getVigentes() {
        List<Autorizacion> res = new ArrayList<>();
        for (Autorizacion a : autorizaciones)
            if (!a.isUsada()) res.add(a);
        return res;
    }

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
