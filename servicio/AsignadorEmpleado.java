package servicio;

import java.util.List;

import modelo.usuarios.Empleado;

public class AsignadorEmpleado {

    private int indiceActual = 0;

    public Empleado obtenerSiguienteEmpleado() {
        List<Empleado> empleados = Repositorio.getEmpleados();

        Empleado empleado = empleados.get(indiceActual);

        indiceActual = (indiceActual + 1) % empleados.size();

        return empleado;
    }
}
