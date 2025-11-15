/**
 * Configuración fija del mapa del juego.
 * Define las lianas disponibles y las reglas básicas
 * para posiciones especiales como la meta y el abismo.
 */
public class GameConfig {

    /**
     * Crea todas las lianas del nivel.
     * <p>
     * Este método representa un mapa fijo. Para cambiar el nivel,
     * basta con ajustar los valores retornados aquí.
     * </p>
     *
     * @return arreglo con todas las lianas disponibles
     */
    public static Vine[] createVines() {
        return new Vine[] {
            new Vine(Integer.valueOf(0), Integer.valueOf(10), Integer.valueOf(5), Integer.valueOf(20)),
            new Vine(Integer.valueOf(1), Integer.valueOf(20), Integer.valueOf(8), Integer.valueOf(22)),
            new Vine(Integer.valueOf(2), Integer.valueOf(30), Integer.valueOf(6), Integer.valueOf(24))
            // Agrega aquí más lianas según tu nivel real.
        };
    }

    /**
     * Indica si una posición corresponde a la meta del juego
     * (la posición donde el jugador gana una vida y aumenta el nivel).
     *
     * @param x coordenada X a evaluar
     * @param y coordenada Y a evaluar
     * @return true si la posición corresponde a la meta
     */
    public static Boolean isGoalPosition(Integer x, Integer y) {
        // Ejemplo: meta entre x = [40,44] y y = 4
        return (x >= 40 && x <= 44 && y.equals(Integer.valueOf(4)));
    }

    /**
     * Indica si una coordenada vertical se considera abismo.
     *
     * @param y coordenada Y a evaluar
     * @return true si la posición corresponde a abismo
     */
    public static Boolean isAbyss(Integer y) {
        // Ejemplo: todo lo que esté por debajo de 30 es abismo
        return y > 30;
    }
}
