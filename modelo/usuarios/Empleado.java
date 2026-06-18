package modelo.usuarios;

import modelo.abstractas.Usuario;

public class Empleado extends Usuario {

    private int codigoEmpleado;
    private String nombre;

    public Empleado(int codigoEmpleado, String nombre) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombre = nombre;
    }
}
