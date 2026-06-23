package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.espacios.Reserva;

public class RepositorioReservas {

    private List<Reserva> reservas = new ArrayList<>();

    public void agregar(Reserva r) {
        reservas.add(r); }

    public List<Reserva> getReservas() {
        return reservas; }
}
