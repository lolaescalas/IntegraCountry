package patrones.factory;

import enums.NivelRiesgo;
import enums.Prioridad;
import enums.TipoServicio;
import modelo.abstractas.Solicitud;
import modelo.solicitudes.IncidenteSeguridad;
import modelo.solicitudes.Reclamo;
import modelo.solicitudes.TareaMantenimiento;
import repositorio.RepositorioEmpleados;
import servicio.AsignadorEmpleado;

public class SolicitudFactory {

    private AsignadorEmpleado asignador;

    public SolicitudFactory() {
        RepositorioEmpleados repositorio = new RepositorioEmpleados();
        this.asignador = new AsignadorEmpleado(repositorio);
    }

    public Solicitud crearReclamo(Prioridad prioridad) {
        return new Reclamo(asignador, prioridad);
    }

    public Solicitud crearTarea(TipoServicio tipoServicio) {
        return new TareaMantenimiento(asignador, tipoServicio);
    }

    public Solicitud crearIncidente(NivelRiesgo nivelRiesgo) {
        return new IncidenteSeguridad(asignador, nivelRiesgo);
    }
}