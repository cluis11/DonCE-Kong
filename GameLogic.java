import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lógica principal del juego en el servidor.
 * Mantiene y actualiza el estado del jugador, cocodrilos, frutas y lianas.
 */
public class GameLogic {

    private final Vine[] vines;
    private final CrocManager crocManager;
    private final List<Fruit> fruits;

    private final Player player;
    private Integer level = Integer.valueOf(1);
    private Float speedMul = Float.valueOf(1.0f);

    /** Constructor: inicializa mapa, jugador y manejadores. */
    public GameLogic() {
        this.vines = GameConfig.createVines();
        this.crocManager = new CrocManager();
        this.fruits = new ArrayList<Fruit>();
        this.player = new Player(Integer.valueOf(5), Integer.valueOf(25), Integer.valueOf(3));
    }

    /** @return jugador del juego */
    public Player getPlayer() { return player; }

    /** @return cocodrilos activos */
    public Iterable<Croc> getCrocs() { return crocManager.getAllCrocs(); }

    /** @return frutas activas */
    public List<Fruit> getFruits() { return fruits; }

    /** @return arreglo de lianas */
    public Vine[] getVines() { return vines; }

    /** @return nivel actual */
    public Integer getLevel() { return level; }

    /** @return multiplicador de velocidad */
    public Float getSpeedMul() { return speedMul; }

    // --- ADMIN ---

    public Boolean adminSpawnRedCroc(Integer vineId, Integer y, Integer baseSpeed) {
        Vine v = findVine(vineId);
        if (v == null) return Boolean.FALSE;
        Integer speed = Integer.valueOf(Math.max(1, Math.round(baseSpeed * speedMul)));
        return Boolean.valueOf(crocManager.spawnRed(v, y, speed) != null);
    }

    public Boolean adminSpawnBlueCroc(Integer vineId, Integer y, Integer baseSpeed) {
        Vine v = findVine(vineId);
        if (v == null) return Boolean.FALSE;
        Integer speed = Integer.valueOf(Math.max(1, Math.round(baseSpeed * speedMul)));
        return Boolean.valueOf(crocManager.spawnBlue(v, y, speed) != null);
    }

    public void adminCreateFruit(Integer x, Integer y, Integer points) {
        fruits.add(new Fruit(x, y, points));
    }

    public void adminDeleteFruitAt(Integer x, Integer y) {
        Iterator<Fruit> it = fruits.iterator();
        while (it.hasNext()) {
            Fruit f = it.next();
            if (f.getX().equals(x) && f.getY().equals(y)) {
                it.remove();
                return;
            }
        }
    }

    // ---SNAPSHOT ---
        /**
     * Construye un snapshot completo del estado actual del juego.
     *
     * @return instancia de GameSnapshot con jugador, cocodrilos, frutas y estado global
     */
    public GameSnapshot buildSnapshot() {
        // 1. Snapshot del jugador
        GameSnapshot.PlayerSnapshot pSnap =
            new GameSnapshot.PlayerSnapshot(
                player.getX(),
                player.getY(),
                player.getLives(),
                player.getScore(),
                player.isOnVine()
            );

        // 2. Snapshot de cocodrilos
        List<GameSnapshot.CrocSnapshot> crocSnaps =
            new ArrayList<GameSnapshot.CrocSnapshot>();

        for (Croc c : crocManager.getAllCrocs()) {
            if (c != null) {
                String type;
                if (c instanceof RedCroc) {
                    type = "RED";
                } else if (c instanceof BlueCroc) {
                    type = "BLUE";
                } else {
                    type = "UNKNOWN";
                }

                GameSnapshot.CrocSnapshot cs =
                    new GameSnapshot.CrocSnapshot(
                        type,
                        c.getVine().getId(),
                        c.getX(),
                        c.getY(),
                        c.isAlive()
                    );
                crocSnaps.add(cs);
            }
        }

        // 3. Snapshot de frutas
        List<GameSnapshot.FruitSnapshot> fruitSnaps =
            new ArrayList<GameSnapshot.FruitSnapshot>();

        for (Fruit f : fruits) {
            GameSnapshot.FruitSnapshot fs =
                new GameSnapshot.FruitSnapshot(
                    f.getX(),
                    f.getY(),
                    f.getPoints(),
                    f.isActive()
                );
            fruitSnaps.add(fs);
        }

        // 4. Snapshot global
        return new GameSnapshot(
            pSnap,
            crocSnaps,
            fruitSnaps,
            level,
            speedMul
        );
    }


    // --- LOOP DE JUEGO ---

    public void updatePlayerFromClient(Integer newX, Integer newY) {
        if (GameConfig.isAbyss(newY)) {
            handlePlayerFall();
            return;
        }

        player.setPosition(newX, newY);

        Boolean onVine = isOnAnyVine(newX, newY);
        player.setOnVine(onVine);

        if (GameConfig.isGoalPosition(newX, newY)) {
            handleWin();
            return;
        }

        checkCrocCollisions();
        checkFruitCollisions();
    }

    public void updateEnemies() {
        crocManager.updateAll();
    }

    // --- REGLAS INTERNAS ---

    private void handlePlayerFall() {
        player.loseLife();
        if (player.getLives() <= 0) {
            // game over aquí si quieres
        } else {
            respawnPlayer();
        }
    }

    private void handleWin() {
        player.gainLife();
        level = Integer.valueOf(level + 1);
        speedMul = Float.valueOf(speedMul + 0.25f);
        resetLevel();
    }

    private void resetLevel() {
        fruits.clear();
        crocManager.clear();
        respawnPlayer();
    }

    private void respawnPlayer() {
        player.setPosition(Integer.valueOf(5), Integer.valueOf(25));
        player.setOnVine(Boolean.FALSE);
    }

    private void checkCrocCollisions() {
        Integer px = player.getX();
        Integer py = player.getY();
        for (Croc c : crocManager.getAllCrocs()) {
            if (c != null && c.isAlive()) {
                if (c.getX().equals(px) && c.getY().equals(py)) {
                    player.loseLife();
                    if (player.getLives() > 0) respawnPlayer();
                    return;
                }
            }
        }
    }

    private void checkFruitCollisions() {
        Integer px = player.getX();
        Integer py = player.getY();
        for (Fruit f : fruits) {
            if (f.isActive() && f.getX().equals(px) && f.getY().equals(py)) {
                player.addScore(f.getPoints());
                f.collect();
            }
        }
    }

    private Boolean isOnAnyVine(Integer x, Integer y) {
        for (Vine v : vines) {
            if (v.contains(x, y)) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Vine findVine(Integer vineId) {
        for (Vine v : vines) {
            if (v.getId().equals(vineId)) return v;
        }
        return null;
    }
}
