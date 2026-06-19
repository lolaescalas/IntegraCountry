package modelo.abstractas;

import java.util.ArrayList;
import java.util.List;

import modelo.usuarios.Empleado;
import patrones.observer.IObservable;
import patrones.observer.IObservador;
import patrones.state.IEstadoSolicitud;
import patrones.state.EstadoPendiente;
import servicio.AsignadorEmpleado;

public abstract class Solicitud implements IObservable {
    
    private IEstadoSolicitud estado;
    private Empleado empleado;
    private AsignadorEmpleado asignador;
    private List<IObservable> observadores = new ArrayList<>();


    public Solicitud(AsignadorEmpleado asignador) {
        this.asignador = asignador; 
        this.estado = new EstadoPendiente();
    }

    @Override
    public void agregar(IObservador o) {
        observadores.add(o);
    }

    @Override
    public void eliminar(IObservador o) {
        observadores.remove(o);
    }

    @Override
    public void notificar() {
        String mensaje = "La solicitud cambio al estado: " + estado.obtenerNombre();
        for (IObservador o : observadores) {
            o.actualizar(mensaje);
        }
    }

    public IEstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(IEstadoSolicitud estado) {
        this.estado = estado;
        notificar();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public AsignadorEmpleado getAsignador() {
        return asignador;
    }
}