import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lógica principal del juego (lado servidor, sin red).
 * - Mantiene jugador(es), crocs, frutas, vines fijas.
 * - Aplica reglas al recibir posiciones desde C.
 */
public class GameLogic {

    private final Vine[] vines;
    private final CrocManager crocManager;
    private final List<Fruit> fruits;

    private final Player player;      // si luego tienes 2, se extiende
    private int level = 1;
    private float speedMul = 1.0f;

    public GameLogic() {
        this.vines = GameConfig.createVines();
        this.crocManager = new CrocManager();
        this.fruits = new ArrayList<>();

        // Posición inicial de Jr (ajusta según tu mapa)
        this.player = new Player(5, 25, 3);
    }

    public Player getPlayer() { return player; }
    public Iterable<Croc> getCrocs() { return crocManager.getAllCrocs(); }
    public List<Fruit> getFruits() { return fruits; }
    public Vine[] getVines() { return vines; }

    public int getLevel() { return level; }
    public float getSpeedMul() { return speedMul; }

    // ================== Admin API ==================

    public boolean adminSpawnRedCroc(int vineId, int y, int baseSpeed) {
        Vine v = findVine(vineId);
        if (v == null) return false;
        int speed = Math.max(1, Math.round(baseSpeed * speedMul));
        return crocManager.spawnRed(v, y, speed) != null;
    }

    public boolean adminSpawnBlueCroc(int vineId, int y, int baseSpeed) {
        Vine v = findVine(vineId);
        if (v == null) return false;
        int speed = Math.max(1, Math.round(baseSpeed * speedMul));
        return crocManager.spawnBlue(v, y, speed) != null;
    }

    public void adminCreateFruit(int x, int y, int points) {
        fruits.add(new Fruit(x, y, points));
    }

    public void adminDeleteFruitAt(int x, int y) {
        Iterator<Fruit> it = fruits.iterator();
        while (it.hasNext()) {
            Fruit f = it.next();
            if (f.getX() == x && f.getY() == y) {
                it.remove();
                return;
            }
        }
    }

    // ================== Loop del servidor ==================

    /**
     * El cliente C envía la posición nueva del jugador.
     * Aquí la validamos y actualizamos estado del juego.
     */
    public void updatePlayerFromClient(int newX, int newY) {
        // Chequear abismo
        if (GameConfig.isAbyss(newY)) {
            handlePlayerFall();
            return;
        }

        // Actualizar posición
        player.setPosition(newX, newY);

        // Marcar si está en una vine
        boolean onVine = isOnAnyVine(newX, newY);
        player.setOnVine(onVine);

        // Verificar win
        if (GameConfig.isGoalPosition(newX, newY)) {
            handleWin();
            return;
        }

        // Colisiones con crocs
        checkCrocCollisions();

        // Colisiones con frutas
        checkFruitCollisions();
    }

    /** Llamar cada tick del servidor para mover crocs. */
    public void updateEnemies() {
        crocManager.updateAll();
    }

    // ================== Reglas internas ==================

    private void handlePlayerFall() {
        player.loseLife();
        if (player.getLives() <= 0) {
            // game over para ese jugador
            // aquí podrías marcar un flag o enviar evento
        } else {
            respawnPlayer();
        }
    }

    private void handleWin() {
        player.gainLife();
        level++;
        speedMul += 0.25f; // o el factor que pida la tarea
        resetLevel();
    }

    private void resetLevel() {
        // Limpia crocs y frutas, respawnea jugador, etc.
        // Simplificado:
        fruits.clear();
        // no hay método clear en CrocManager, pero podrías recrearlo o añadir clear().
        respawnPlayer();
    }

    private void respawnPlayer() {
        // Ajusta a la posición inicial de tu mapa
        player.setPosition(5, 25);
        player.setOnVine(false);
    }

    private void checkCrocCollisions() {
        int px = player.getX();
        int py = player.getY();

        for (Croc c : crocManager.getAllCrocs()) {
            if (c != null && c.isAlive()) {
                if (c.getX() == px && c.getY() == py) {
                    player.loseLife();
                    if (player.getLives() <= 0) {
                        // game over
                    } else {
                        respawnPlayer();
                    }
                    return;
                }
            }
        }
    }

    private void checkFruitCollisions() {
        int px = player.getX();
        int py = player.getY();

        for (Fruit f : fruits) {
            if (f.isActive() && f.getX() == px && f.getY() == py) {
                player.addScore(f.getPoints());
                f.collect();
            }
        }
    }

    private boolean isOnAnyVine(int x, int y) {
        for (Vine v : vines) {
            if (v.contains(x, y)) return true;
        }
        return false;
    }

    private Vine findVine(int vineId) {
        for (Vine v : vines) {
            if (v.getId() == vineId) return v;
        }
        return null;
    }
}
