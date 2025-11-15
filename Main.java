/**
 * Programa de prueba para la lógica del juego con snapshots.
 */
public class Main {

    /**
     * Punto de entrada principal.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) throws Exception {

        GameLogic game = new GameLogic();

        System.out.println("=== ESTADO INICIAL ===");
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== ADMIN: SPAWN RED CROC EN VINE 0 ===");
        Boolean okRed = game.adminSpawnRedCroc(
            Integer.valueOf(0),
            Integer.valueOf(10),
            Integer.valueOf(1)
        );
        System.out.println("Spawn red croc en vine 0: " + okRed);
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== ADMIN: CREAR FRUTA EN (10, 8) ===");
        game.adminCreateFruit(
            Integer.valueOf(10),
            Integer.valueOf(8),
            Integer.valueOf(300)
        );
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== TICK 1: UPDATE ENEMIGOS + PLAYER -> (10, 15) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(10), Integer.valueOf(15));
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== TICK 2: UPDATE ENEMIGOS + PLAYER -> (10, 10) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(10), Integer.valueOf(10));
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== TICK 3: PLAYER -> (10, 8) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(10), Integer.valueOf(8));
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== TICK 4: PLAYER -> POSICIÓN GOAL (42, 4) ===");
        game.updateEnemies();
        game.updatePlayerFromClient(Integer.valueOf(42), Integer.valueOf(4));
        printSnapshot(game.buildSnapshot());

        System.out.println("\n=== FIN DE PRUEBA ===");
    }

    /**
     * Imprime en consola la información contenida en un snapshot.
     *
     * @param snapshot snapshot a imprimir
     */
    private static void printSnapshot(GameSnapshot snapshot) {
        GameSnapshot.PlayerSnapshot p = snapshot.getPlayer();

        System.out.println("Jugador:");
        System.out.println("  Pos     : (" + p.getX() + ", " + p.getY() + ")");
        System.out.println("  Vidas   : " + p.getLives());
        System.out.println("  Score   : " + p.getScore());
        System.out.println("  OnVine  : " + p.getOnVine());

        System.out.println("Nivel / Velocidad:");
        System.out.println("  Level   : " + snapshot.getLevel());
        System.out.println("  SpeedMul: " + snapshot.getSpeedMul());

        System.out.println("Cocodrilos:");
        Integer i = Integer.valueOf(0);
        for (GameSnapshot.CrocSnapshot c : snapshot.getCrocs()) {
            System.out.println(
                "  #" + i +
                " tipo=" + c.getType() +
                " vineId=" + c.getVineId() +
                " pos=(" + c.getX() + "," + c.getY() + ")" +
                " alive=" + c.getAlive()
            );
            i = Integer.valueOf(i + 1);
        }

        System.out.println("Frutas:");
        for (GameSnapshot.FruitSnapshot f : snapshot.getFruits()) {
            System.out.println(
                "  pos=(" + f.getX() + "," + f.getY() + ")" +
                " pts=" + f.getPoints() +
                " active=" + f.getActive()
            );
        }
    }
}
