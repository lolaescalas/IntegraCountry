package repositorio;

import java.util.List;

import modelo.usuarios.Empleado;

public class RepositorioEmpleados {

    private static List<Empleado> empleados = List.of(
        new Empleado(1, "Juan Montero"),
new Empleado(2, "María Lopez"),
new Empleado(3, "Pedro Alcorta"),
new Empleado(4, "Ana Fernández"),
new Empleado(5, "Carlos Gómez"),
new Empleado(6, "Lucía Martínez"),
new Empleado(7, "Diego Rodríguez"),
new Empleado(8, "Valentina Pérez"),
new Empleado(9, "Martín Sánchez"),
new Empleado(10, "Sofía Herrera"),
new Empleado(11, "Nicolás Castro"),
new Empleado(12, "Camila Díaz"),
new Empleado(13, "Joaquín Romero"),
new Empleado(14, "Florencia Torres"),
new Empleado(15, "Agustín Vega"),
new Empleado(16, "Julieta Navarro"),
new Empleado(17, "Tomás Rojas"),
new Empleado(18, "Milagros Acuña"),
new Empleado(19, "Federico Molina"),
new Empleado(20, "Paula Giménez")
    );

    public static List<Empleado> getEmpleados() {
        return empleados;
    }
}
