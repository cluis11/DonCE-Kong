/**
 * Clase abstracta base para todos los cocodrilos.
 * Cada cocodrilo pertenece a una liana fija y se mueve verticalmente.
 */
public abstract class Croc {

    /** Liana fija a la que pertenece el cocodrilo. */
    protected final Vine vine;

    /** Posición vertical actual dentro de la liana. */
    protected Integer y;

    /** Estado de vida del cocodrilo. */
    protected Boolean alive = true;

    /**
     * Crea un cocodrilo asociado a una liana específica.
     *
     * @param vine liana fija del mapa
     * @param initialY posición inicial vertical
     */
    protected Croc(Vine vine, Integer initialY) {
        this.vine = vine;
        this.y = initialY;
    }

    /** @return liana a la que pertenece */
    public Vine getVine() { return vine; }

    /** @return coordenada X (la misma que la de la liana) */
    public Integer getX() { return vine.getX(); }

    /** @return coordenada Y actual */
    public Integer getY() { return y; }

    /** @return true si el cocodrilo está vivo */
    public Boolean isAlive() { return alive; }

    /** Actualiza el movimiento del cocodrilo. */
    public abstract void update();

    /** Marca al cocodrilo como muerto. */
    protected void kill() {
        this.alive = false;
    }
}