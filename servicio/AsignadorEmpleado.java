package servicio;

import java.util.List;

import modelo.usuarios.Empleado;
import repositorio.RepositorioEmpleados;

public class AsignadorEmpleado {

    private RepositorioEmpleados repositorio;
    private int indiceActual = 0;

    public AsignadorEmpleado() {
        this.repositorio = new RepositorioEmpleados();
    }

    public Empleado obtenerSiguienteEmpleado() {
        List<Empleado> empleados = RepositorioEmpleados.getEmpleados();

        Empleado empleado = empleados.get(indiceActual);

        indiceActual = (indiceActual + 1) % empleados.size();

        return empleado;
    }
}
