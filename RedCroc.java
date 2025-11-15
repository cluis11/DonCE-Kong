/**
 * Cocodrilo rojo.
 * Se mueve de arriba a abajo dentro de su liana y rebota en los extremos.
 */
public class RedCroc extends Croc {

    private Integer dir = 1; // 1 = baja, -1 = sube
    private final Integer speed;

    /**
     * Crea un cocodrilo rojo asociado a una liana.
     *
     * @param vine liana en la que se moverá
     * @param initialY posición inicial
     * @param speed velocidad de desplazamiento
     */
    public RedCroc(Vine vine, Integer initialY, Integer speed) {
        super(vine, initialY);
        this.speed = Math.max(1, speed);
    }

    /** Actualiza la posición del cocodrilo según su dirección actual. */
    @Override
    public void update() {
        if (!alive) return;

        y += dir * speed;

        if (y >= vine.getYBottom()) {
            y = vine.getYBottom();
            dir = -1;
        } else if (y <= vine.getYTop()) {
            y = vine.getYTop();
            dir = 1;
        }
    }
}
