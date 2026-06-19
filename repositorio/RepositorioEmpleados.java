package repositorio;

import java.util.List;

import modelo.usuarios.Empleado;

public class RepositorioEmpleados {

    private static List<Empleado> empleados = List.of(
    new Empleado(1, "Juan Montero", "30111222", "juan.montero@barrio.com", "1130011222"),
    new Empleado(2, "Maria Lopez", "28999888", "maria.lopez@barrio.com", "1139998888"),
    new Empleado(3, "Pedro Alcorta", "31222333", "pedro.alcorta@barrio.com", "1131222333"),
    new Empleado(4, "Ana Fernandez", "32555444", "ana.fernandez@barrio.com", "1132555444"),
    new Empleado(5, "Carlos Gomez", "27888777", "carlos.gomez@barrio.com", "1127888777"),
    new Empleado(6, "Lucia Martinez", "33666111", "lucia.martinez@barrio.com", "1133666111"),
    new Empleado(7, "Diego Rodriguez", "29999111", "diego.rodriguez@barrio.com", "1129999111"),
    new Empleado(8, "Valentina Perez", "34444222", "valentina.perez@barrio.com", "1134444222"),
    new Empleado(9, "Martin Sanchez", "28777123", "martin.sanchez@barrio.com", "1128777123"),
    new Empleado(10, "Sofia Herrera", "35555333", "sofia.herrera@barrio.com", "1135555333"),
    new Empleado(11, "Nicolas Castro", "32111999", "nicolas.castro@barrio.com", "1132111999"),
    new Empleado(12, "Camila Diaz", "34777888", "camila.diaz@barrio.com", "1134777888"),
    new Empleado(13, "Joaquin Romero", "29333444", "joaquin.romero@barrio.com", "1129333444"),
    new Empleado(14, "Florencia Torres", "35888999", "florencia.torres@barrio.com", "1135888999"),
    new Empleado(15, "Agustin Vega", "30666777", "agustin.vega@barrio.com", "1130666777")
);

    public static List<Empleado> getEmpleados() {
        return empleados;
    }
}
