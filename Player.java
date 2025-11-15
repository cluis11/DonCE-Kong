/**
 * Representa al jugador principal (Donkey Kong Jr).
 * Su posición es enviada por el cliente C y validada en el servidor.
 */
public class Player {

    private Integer x;
    private Integer y;
    private Integer lives;
    private Integer score;
    private Boolean onVine;

    /**
     * Crea un nuevo jugador.
     *
     * @param x posición inicial en X
     * @param y posición inicial en Y
     * @param lives cantidad inicial de vidas
     */
    public Player(Integer x, Integer y, Integer lives) {
        this.x = x;
        this.y = y;
        this.lives = lives;
        this.score = 0;
        this.onVine = false;
    }

    /** @return coordenada X del jugador */
    public Integer getX() { return x; }

    /** @return coordenada Y del jugador */
    public Integer getY() { return y; }

    /** @return número actual de vidas */
    public Integer getLives() { return lives; }

    /** @return puntaje actual */
    public Integer getScore() { return score; }

    /** @return true si el jugador se encuentra en una liana */
    public Boolean isOnVine() { return onVine; }

    /**
     * Actualiza la posición del jugador.
     *
     * @param x nueva coordenada X
     * @param y nueva coordenada Y
     */
    public void setPosition(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Define si el jugador se encuentra en una liana.
     *
     * @param onVine true si está sobre una liana
     */
    public void setOnVine(Boolean onVine) {
        this.onVine = onVine;
    }

    /**
     * Incrementa el puntaje del jugador.
     *
     * @param points puntos a sumar
     */
    public void addScore(Integer points) {
        this.score += points;
    }

    /** Resta una vida al jugador. */
    public void loseLife() {
        if (lives > 0) lives--;
    }

    /** Agrega una vida al jugador. */
    public void gainLife() {
        lives++;
    }
}
