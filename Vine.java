/**
 * Representa una liana fija del mapa del juego.
 * Cada Vine tiene una posición horizontal (x) y un rango vertical (yTop, yBottom).
 * Estas lianas son fijas durante toda la ejecución del juego.
 */
public class Vine {

    /** Identificador lógico de la liana (por ejemplo 0, 1, 2...). */
    private final Integer id;

    /** Coordenada horizontal (columna fija del mapa). */
    private final Integer x;

    /** Coordenada vertical superior (inicio de la liana). */
    private final Integer yTop;

    /** Coordenada vertical inferior (fin de la liana). */
    private final Integer yBottom;

    /**
     * Crea una nueva instancia de Vine.
     *
     * @param id identificador lógico de la liana
     * @param x coordenada horizontal fija
     * @param yTop posición vertical superior
     * @param yBottom posición vertical inferior
     */
    public Vine(Integer id, Integer x, Integer yTop, Integer yBottom) {
        this.id = id;
        this.x = x;
        this.yTop = yTop;
        this.yBottom = yBottom;
    }

    /** @return identificador de la liana */
    public Integer getId() { return id; }

    /** @return coordenada X fija de la liana */
    public Integer getX() { return x; }

    /** @return límite superior en Y */
    public Integer getYTop() { return yTop; }

    /** @return límite inferior en Y */
    public Integer getYBottom() { return yBottom; }

    /**
     * Verifica si una posición (px, py) se encuentra dentro de la liana.
     *
     * @param px coordenada X a verificar
     * @param py coordenada Y a verificar
     * @return true si la posición está dentro de la liana
     */
    public Boolean contains(Integer px, Integer py) {
        return px.equals(x) && py >= yTop && py <= yBottom;
    }
}