/**
 * Representa una fruta en el mapa del juego.
 * Al ser recogida, otorga puntos al jugador.
 */
public class Fruit {

    private final Integer x;
    private final Integer y;
    private final Integer points;
    private Boolean active = true;

    /**
     * Crea una fruta en una posición específica.
     *
     * @param x coordenada X
     * @param y coordenada Y
     * @param points cantidad de puntos que otorga
     */
    public Fruit(Integer x, Integer y, Integer points) {
        this.x = x;
        this.y = y;
        this.points = points;
    }

    /** @return coordenada X de la fruta */
    public Integer getX() { return x; }

    /** @return coordenada Y de la fruta */
    public Integer getY() { return y; }

    /** @return cantidad de puntos que otorga */
    public Integer getPoints() { return points; }

    /** @return true si la fruta sigue activa */
    public Boolean isActive() { return active; }

    /** Marca la fruta como recolectada. */
    public void collect() {
        active = false;
    }
}