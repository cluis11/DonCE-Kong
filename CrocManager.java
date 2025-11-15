import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Administra todos los cocodrilos activos en el juego.
 * Solo se permite un cocodrilo por liana.
 */
public class CrocManager {

    private final Map<Integer, Croc> crocsByVine = new HashMap<>();

    /**
     * Verifica si una liana puede tener un nuevo cocodrilo.
     *
     * @param vine liana a verificar
     * @return true si está libre o el cocodrilo anterior está muerto
     */
    public Boolean canSpawnOn(Vine vine) {
        Croc existing = crocsByVine.get(vine.getId());
        return existing == null || !existing.isAlive();
    }

    /**
     * Crea un cocodrilo rojo si la liana está libre.
     */
    public Croc spawnRed(Vine vine, Integer initialY, Integer speed) {
        if (!canSpawnOn(vine)) return null;
        Croc c = new RedCroc(vine, initialY, speed);
        crocsByVine.put(vine.getId(), c);
        return c;
    }

    /**
     * Crea un cocodrilo azul si la liana está libre.
     */
    public Croc spawnBlue(Vine vine, Integer initialY, Integer speed) {
        if (!canSpawnOn(vine)) return null;
        Croc c = new BlueCroc(vine, initialY, speed);
        crocsByVine.put(vine.getId(), c);
        return c;
    }

    /** Actualiza todos los cocodrilos activos. */
    public void updateAll() {
        for (Croc c : crocsByVine.values()) {
            if (c != null && c.isAlive()) c.update();
        }
    }

    /** @return colección de todos los cocodrilos */
    public Collection<Croc> getAllCrocs() {
        return crocsByVine.values();
    }

    /** Elimina todos los cocodrilos muertos. */
    public void removeDead() {
        crocsByVine.entrySet().removeIf(e -> e.getValue() == null || !e.getValue().isAlive());
    }

    /** Limpia todos los cocodrilos. */
    public void clear() {
        crocsByVine.clear();
    }
}