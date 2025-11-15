/**
 * Cocodrilo azul.
 * Desciende por la liana hasta salir de ella y luego desaparece.
 */
public class BlueCroc extends Croc {

    private final Integer speed;

    /**
     * Crea un cocodrilo azul.
     *
     * @param vine liana por la que descenderá
     * @param initialY posición inicial
     * @param speed velocidad de descenso
     */
    public BlueCroc(Vine vine, Integer initialY, Integer speed) {
        super(vine, initialY);
        this.speed = Math.max(1, speed);
    }

    /** Mueve el cocodrilo hacia abajo; muere al salir de la liana. */
    @Override
    public void update() {
        if (!alive) return;

        y += speed;

        if (y > vine.getYBottom()) {
            kill();
        }
    }
}