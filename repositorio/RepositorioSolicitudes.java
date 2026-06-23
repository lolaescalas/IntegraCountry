package repositorio;

import java.util.ArrayList;
import java.util.List;

import modelo.abstractas.Solicitud;

public class RepositorioSolicitudes {

    private List<Solicitud> solicitudes = new ArrayList<>();

    public void agregar(Solicitud solicitud) {
        solicitudes.add(solicitud); }

    public List<Solicitud> getSolicitudes() {
        return solicitudes; }
}
