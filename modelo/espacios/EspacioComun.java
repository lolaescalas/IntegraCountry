package modelo.espacios;
public class EspacioComun {
    private String nombre;
    private String tipo;
    private boolean disponible;
    public EspacioComun(String nombre, String tipo) { this.nombre = nombre; this.tipo = tipo; this.disponible = true; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}
