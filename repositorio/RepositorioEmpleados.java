package repositorio;
import java.util.List;
import modelo.usuarios.Empleado;

public class RepositorioEmpleados {

    private List<Empleado> empleados = List.of(
        new Empleado(1, "Juan Montero", "30111222", "juan.montero@barrio.com", "1130011222"),
        new Empleado(2, "Maria Lopez", "28999888", "maria.lopez@barrio.com", "1139998888"),
        new Empleado(3, "Pedro Alcorta", "31222333", "pedro.alcorta@barrio.com", "1131222333"),
        new Empleado(4, "Ana Fernandez", "32555444", "ana.fernandez@barrio.com", "1132555444"),
        new Empleado(5, "Carlos Gomez", "27888777", "carlos.gomez@barrio.com", "1127888777")
    );

    public List<Empleado> getEmpleados() {
        return empleados; }
        
}
