package modelo.espacios;
import java.util.ArrayList;
import java.util.List;

public class Barrio {

    private String nombre;
    private String direccion;
    private List<Lote> lotes = new ArrayList<>();

    public Barrio(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public void agregarLote(Lote lote) {
        lotes.add(lote);
    }

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public List<Lote> getLotes() { return lotes; }

}
