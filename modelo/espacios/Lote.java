package modelo.espacios;
public class Lote {
    private int numero; private String estado;
    public Lote(int numero, String estado) { this.numero = numero; this.estado = estado; }
    public int getNumero() { return numero; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getEtiqueta() { return String.format("L-%02d", numero); }
    @Override public String toString() { return getEtiqueta(); }
    public Lote(int numero) { this.numero = numero; }
}
