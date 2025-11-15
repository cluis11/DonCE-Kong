/**
 * Programa de prueba para la lógica del juego.
 * Ejecuta varias interacciones para verificar GameLogic.
 */
public class Main {

    /**
     * Punto de entrada principal.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {

        GameLogic game = new GameLogic();

        System.out.println("=== ESTADO INICIAL ===");
        printState(game);

        System.out.println("\n=== ADMIN: SPAWN RED CROC EN VINE 0 ===");
        Boolean okRed = game.adminSpawnRedCroc(Integer.valueOf(0), Integer.valueOf(10), Integer.valueOf(1));
        System.out.println("Spawn red croc en vine 0: " + okRed);
        printState(game);

        System.out.println("\n=== ADMIN: CREAR FRUTA EN (10, 8) ===");
        game.adminCreateFruit(Integer.valueOf(10), Integer.valueOf(8), Integer.valueOf(300));
        printState(game);

        System.out.println("\n=== TICK 1: UPDATE ENEMIGOS + PLAYER -> (10, 15) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(10), Integer.valueOf(15));
        printState(game);

        System.out.println("\n=== TICK 2: UPDATE ENEMIGOS + PLAYER -> (10, 10) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(10), Integer.valueOf(10));
        printState(game);

        System.out.println("\n=== TICK 3: PLAYER -> (10, 8) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(10), Integer.valueOf(8));
        printState(game);

        System.out.println("\n=== TICK 4: PLAYER -> POSICIÓN GOAL (42, 4) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(42), Integer.valueOf(4));
        printState(game);

        System.out.println("\n=== FIN DE PRUEBA ===");
    }

    /**
     * Imprime el estado actual del juego.
     *
     * @param game lógica del juego
     */
    private static void printState(GameLogic game) {
        Player p = game.getPlayer();

        System.out.println("Jugador:");
        System.out.println("  Pos     : (" + p.getX() + ", " + p.getY() + ")");
        System.out.println("  Vidas   : " + p.getLives());
        System.out.println("  Score   : " + p.getScore());
        System.out.println("  OnVine  : " + p.isOnVine());

        System.out.println("Nivel / Velocidad:");
        System.out.println("  Level   : " + game.getLevel());
        System.out.println("  SpeedMul: " + game.getSpeedMul());

        System.out.println("Cocodrilos:");
        Integer i = Integer.valueOf(0);
        for (Croc c : game.getCrocs()) {
            if (c != null && c.isAlive()) {
                System.out.println(
                    "  #" + i +
                    " tipo=" + c.getClass().getSimpleName() +
                    " vineId=" + c.getVine().getId() +
                    " pos=(" + c.getX() + "," + c.getY() + ")"
                );
            }
            i = Integer.valueOf(i + 1);
        }

        System.out.println("Frutas activas:");
        for (Fruit f : game.getFruits()) {
            if (f.isActive()) {
                System.out.println("  (" + f.getX() + "," + f.getY() + ") pts=" + f.getPoints());
            }
        }
    }
}
