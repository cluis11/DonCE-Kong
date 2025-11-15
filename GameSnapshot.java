import java.util.List;

/**
 * Representa una "foto" completa del estado del juego en un instante.
 * Este snapshot contiene información del jugador, cocodrilos, frutas
 * y estado global (nivel y multiplicador de velocidad).
 */
public class GameSnapshot {

    /**
     * Información del jugador en el snapshot.
     */
    public static class PlayerSnapshot {
        private final Integer x;
        private final Integer y;
        private final Integer lives;
        private final Integer score;
        private final Boolean onVine;

        /**
         * Crea un snapshot del jugador.
         *
         * @param x posición X
         * @param y posición Y
         * @param lives vidas restantes
         * @param score puntaje actual
         * @param onVine true si está sobre una liana
         */
        public PlayerSnapshot(Integer x, Integer y, Integer lives,
                              Integer score, Boolean onVine) {
            this.x = x;
            this.y = y;
            this.lives = lives;
            this.score = score;
            this.onVine = onVine;
        }

        /** @return posición X del jugador */
        public Integer getX() { return x; }

        /** @return posición Y del jugador */
        public Integer getY() { return y; }

        /** @return vidas del jugador */
        public Integer getLives() { return lives; }

        /** @return puntaje del jugador */
        public Integer getScore() { return score; }

        /** @return true si está sobre una liana */
        public Boolean getOnVine() { return onVine; }
    }

    /**
     * Información de un cocodrilo en el snapshot.
     */
    public static class CrocSnapshot {
        private final String type;
        private final Integer vineId;
        private final Integer x;
        private final Integer y;
        private final Boolean alive;

        /**
         * Crea un snapshot de un cocodrilo.
         *
         * @param type tipo de cocodrilo ("RED" o "BLUE")
         * @param vineId identificador de la liana
         * @param x posición X
         * @param y posición Y
         * @param alive true si está vivo
         */
        public CrocSnapshot(String type, Integer vineId,
                            Integer x, Integer y, Boolean alive) {
            this.type = type;
            this.vineId = vineId;
            this.x = x;
            this.y = y;
            this.alive = alive;
        }

        /** @return tipo de cocodrilo ("RED" o "BLUE") */
        public String getType() { return type; }

        /** @return identificador de la liana asociada */
        public Integer getVineId() { return vineId; }

        /** @return posición X */
        public Integer getX() { return x; }

        /** @return posición Y */
        public Integer getY() { return y; }

        /** @return true si el cocodrilo está vivo */
        public Boolean getAlive() { return alive; }
    }

    /**
     * Información de una fruta en el snapshot.
     */
    public static class FruitSnapshot {
        private final Integer x;
        private final Integer y;
        private final Integer points;
        private final Boolean active;

        /**
         * Crea un snapshot de una fruta.
         *
         * @param x posición X
         * @param y posición Y
         * @param points puntos que otorga
         * @param active true si sigue activa
         */
        public FruitSnapshot(Integer x, Integer y, Integer points, Boolean active) {
            this.x = x;
            this.y = y;
            this.points = points;
            this.active = active;
        }

        /** @return posición X de la fruta */
        public Integer getX() { return x; }

        /** @return posición Y de la fruta */
        public Integer getY() { return y; }

        /** @return puntos que otorga la fruta */
        public Integer getPoints() { return points; }

        /** @return true si la fruta sigue activa */
        public Boolean getActive() { return active; }
    }

    private final PlayerSnapshot player;
    private final List<CrocSnapshot> crocs;
    private final List<FruitSnapshot> fruits;
    private final Integer level;
    private final Float speedMul;

    /**
     * Crea un snapshot completo del juego.
     *
     * @param player snapshot del jugador
     * @param crocs lista de snapshots de cocodrilos
     * @param fruits lista de snapshots de frutas
     * @param level nivel actual
     * @param speedMul multiplicador de velocidad
     */
    public GameSnapshot(PlayerSnapshot player,
                        List<CrocSnapshot> crocs,
                        List<FruitSnapshot> fruits,
                        Integer level, Float speedMul) {
        this.player = player;
        this.crocs = crocs;
        this.fruits = fruits;
        this.level = level;
        this.speedMul = speedMul;
    }

    /** @return snapshot del jugador */
    public PlayerSnapshot getPlayer() { return player; }

    /** @return lista de snapshots de cocodrilos */
    public List<CrocSnapshot> getCrocs() { return crocs; }

    /** @return lista de snapshots de frutas */
    public List<FruitSnapshot> getFruits() { return fruits; }

    /** @return nivel actual del juego */
    public Integer getLevel() { return level; }

    /** @return multiplicador de velocidad actual */
    public Float getSpeedMul() { return speedMul; }
}
