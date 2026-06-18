package modelo.abstractas;

import modelo.usuarios.Empleado;
import patrones.state.IEstadoSolicitud;

public abstract class Solicitud {

    private IEstadoSolicitud estado;
    private Empleado empleado;

    public IEstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(IEstadoSolicitud estado) {
        this.estado = estado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
