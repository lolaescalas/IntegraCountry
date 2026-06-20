package modelo.espacios;

public class Reserva {

    private String fecha;
    private String horario;
    private String estado;
    private EspacioComun espacio;


    public Reserva(String fecha, String horario, EspacioComun espacio) {
        this.fecha = fecha;
        this.horario = horario;
        this.espacio = espacio;
        this.estado = "ACTIVA";       
    }

    public void cancelar() {
        this.estado = "CANCELADA";
        this.espacio.setDisponible(true);
    }

    public String getEstado() {
        return estado;
    }

    public EspacioComun getEspacio() {return espacio; }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

}
