package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.espacios.Lote;
import modelo.expensas.Expensa;

public class RepositorioExpensas {
    private List<Expensa> expensas = new ArrayList<>();

    public void agregar(Expensa e) {
        expensas.add(e); }
        
    public List<Expensa> getExpensas() {
        return expensas; }

    public List<Expensa> getPorLote(Lote lote) {
        List<Expensa> res = new ArrayList<>();
        for (Expensa e : expensas) if (e.getLote() == lote) res.add(e);
        return res;
    }
}
