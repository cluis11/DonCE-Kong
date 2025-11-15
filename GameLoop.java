import java.util.concurrent.atomic.AtomicReference;

/**
 * Representa el loop principal del juego en el servidor.
 * <p>
 * Este GameLoop ejecuta la lógica del juego en intervalos fijos (ticks),
 * aplicando actualización de enemigos y procesando la posición más reciente
 * del jugador enviada externamente (por ejemplo desde el cliente en C).
 * </p>
 */
public class GameLoop implements Runnable {

    /** Lógica central del juego. */
    private final GameLogic gameLogic;

    /** Última posición X/Y reportada por el cliente. */
    private final AtomicReference<PlayerInput> lastInput;

    /** Tiempo en milisegundos entre ticks (frame time). */
    private final Integer tickDurationMs;

    /** Bandera para detener el loop. */
    private volatile Boolean running = Boolean.FALSE;

    /**
     * Crea un GameLoop con un tick rate configurado.
     *
     * @param gameLogic instancia de la lógica del juego
     * @param ticksPerSecond cantidad de ciclos por segundo
     */
    public GameLoop(GameLogic gameLogic, Integer ticksPerSecond) {
        this.gameLogic = gameLogic;
        this.lastInput = new AtomicReference<PlayerInput>(new PlayerInput(5, 25));
        this.tickDurationMs = Integer.valueOf(1000 / ticksPerSecond);
    }

    /**
     * Establece un nuevo input del jugador.
     * Este método sería llamado desde el código de red cuando llegue un (x,y) de C.
     *
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void setPlayerInput(Integer x, Integer y) {
        lastInput.set(new PlayerInput(x, y));
    }

    /**
     * Inicia el loop del juego.
     */
    public void start() {
        running = Boolean.TRUE;
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Detiene el loop del juego.
     */
    public void stop() {
        running = Boolean.FALSE;
    }

    /**
     * Ejecución principal del loop.
     * Aplica lógica de movimiento de enemigos y actualización del jugador.
     */
    @Override
    public void run() {
        while (running) {

            long startTime = System.currentTimeMillis();

            // 1. Actualizar enemigos (crocs)
            gameLogic.updateEnemies();

            // 2. Procesar última posición del jugador
            PlayerInput input = lastInput.get();
            gameLogic.updatePlayerFromClient(input.x, input.y);

            // 3. Aquí, más adelante, generaremos y enviaremos snapshot al cliente C
            // GameSnapshot snapshot = gameLogic.buildSnapshot();
            // sendToClient(snapshot);

            // 4. Dormir hasta el siguiente tick
            long elapsed = System.currentTimeMillis() - startTime;
            long sleep = tickDurationMs - elapsed;
            if (sleep < 1) sleep = 1;

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                // ignorar interrupciones simples
            }
        }
    }

    /**
     * Clase interna sencilla para almacenar la última posición del jugador.
     */
    private static class PlayerInput {
        final Integer x;
        final Integer y;

        PlayerInput(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }
}
