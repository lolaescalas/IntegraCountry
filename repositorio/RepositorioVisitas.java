package repositorio;

import java.util.List;

public class RepositorioVisitas {

    private static List<String> visitas = List.of(
    "30111222",
    "32111333",
    "28999888",
    "27888777",
    "35555333",
    "31222333",
    "32555444",
    "29999111",
    "34444222",
    "28777123",
    "33666111",
    "36666222",
    "31444555",
    "29888776",
    "33222111",
    "34777888",
    "29333444",
    "35888999",
    "30666777",
    "31111444"
);

    public static List<String> getVisitas() {
        return visitas;
    }

    public static void setVisitas(List<String> visitas) {
        RepositorioVisitas.visitas = visitas;
    }
    
}
