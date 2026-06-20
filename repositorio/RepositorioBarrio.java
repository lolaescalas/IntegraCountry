package repositorio;

import java.util.List;

import modelo.espacios.Lote;
import modelo.usuarios.Residente;

public class RepositorioBarrio {

    private static List<Lote> lotes = List.of(
    new Lote(1),
    new Lote(2),
    new Lote(3),
    new Lote(4),
    new Lote(5)
);

    private static List<Residente> residentes = List.of(
    // Lote 1
    new Residente(1, lotes.get(0), "Juan Perez", "30111222", "juan@gmail.com", "1130011222"),
    new Residente(2, lotes.get(0), "Laura Perez", "32111333", "laura@gmail.com", "1132111333"),

    // Lote 2
    new Residente(3, lotes.get(1), "Maria Gomez", "28999888", "maria@gmail.com", "1139998888"),
    new Residente(4, lotes.get(1), "Carlos Gomez", "27888777", "carlos@gmail.com", "1127888777"),
    new Residente(5, lotes.get(1), "Sofia Gomez", "35555333", "sofia@gmail.com", "1135555333"),

    // Lote 3
    new Residente(6, lotes.get(2), "Pedro Sanchez", "31222333", "pedro@gmail.com", "1131222333"),
    new Residente(7, lotes.get(2), "Ana Sanchez", "32555444", "ana@gmail.com", "1132555444"),

    // Lote 4
    new Residente(8, lotes.get(3), "Diego Romero", "29999111", "diego@gmail.com", "1129999111"),
    new Residente(9, lotes.get(3), "Valentina Romero", "34444222", "valentina@gmail.com", "1134444222"),

    // Lote 5
    new Residente(10, lotes.get(4), "Martin Castro", "28777123", "martin@gmail.com", "1128777123"),
    new Residente(11, lotes.get(4), "Lucia Castro", "33666111", "lucia@gmail.com", "1133666111"),
    new Residente(12, lotes.get(4), "Tomas Castro", "36666222", "tomas@gmail.com", "1136666222")
);

    public static List<Lote> getLotes() {
        return lotes;
    }

    public static void setLotes(List<Lote> lotes) {
        RepositorioBarrio.lotes = lotes;
    }

    public static List<Residente> getResidentes() {
        return residentes;
    }

    public static void setResidentes(List<Residente> residentes) {
        RepositorioBarrio.residentes = residentes;
    }


}
