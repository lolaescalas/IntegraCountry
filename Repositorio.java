import java.util.List;

import modelo.usuarios.Empleado;

public class Repositorio {
    
    private static List<Empleado> empleados = List.of(
        new Empleado(1, "Juan Montero"),
        new Empleado(2, "María Rodriguez"),
        new Empleado(3, "Pedro Alcorta")
    );

    public static List<Empleado> getEmpleados() {
        return empleados;
    }

    public static void setEmpleados(List<Empleado> empleados) {
        Repositorio.empleados = empleados;
    }

    
}
