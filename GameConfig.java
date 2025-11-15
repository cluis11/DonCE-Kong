/**
 * Configuración fija del mapa:
 * - Vines disponibles (id, x, yTop, yBottom)
 * - Zona de meta (GOAL) donde el jugador gana.
 */
public class GameConfig {

    // Ejemplo: define tus lianas reales aquí.
    public static Vine[] createVines() {
        return new Vine[] {
            new Vine(0, 10, 5, 20),
            new Vine(1, 20, 8, 22),
            new Vine(2, 30, 6, 24)
            // agrega las que necesites
        };
    }

    // Ejemplo de zona de victoria (puede ser una coordenada o rango).
    public static boolean isGoalPosition(int x, int y) {
        // Ajusta esto a donde está Mario/DK en tu nivel.
        return (x >= 40 && x <= 44 && y == 4);
    }

    // Ejemplo: abismo si y es mayor a cierto límite
    public static boolean isAbyss(int y) {
        // Ajusta según altura de tu mapa
        return y > 30;
    }
}
