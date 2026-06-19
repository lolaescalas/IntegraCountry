package modelo.usuarios;

import modelo.abstractas.Usuario;

public class Empleado extends Usuario {

    private int codigoEmpleado;

    public Empleado(int codigoEmpleado, String nombre, String dni, String email, String telefono) {
        super(nombre, dni, email, telefono);
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

}

