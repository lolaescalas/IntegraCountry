package modelo.correo;
public class Multa {
    private double monto; private String motivo;
    public Multa(double monto, String motivo) { this.monto = monto; this.motivo = motivo; }
    public double getMonto() { return monto; }
    public String getMotivo() { return motivo; }
}
