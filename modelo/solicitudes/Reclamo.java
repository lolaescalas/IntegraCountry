package modelo.solicitudes;

import modelo.enums.Prioridad;
import modelo.abstractas.Solicitud;
import servicio.AsignadorEmpleado;

public class Reclamo extends Solicitud {

    private Prioridad prioridad;

    public Reclamo(AsignadorEmpleado asignador, Prioridad prioridad) {
        super(asignador);
        this.prioridad = prioridad;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
}