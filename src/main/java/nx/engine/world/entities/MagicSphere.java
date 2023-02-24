package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.particles.Particle;
import nx.game.App;
import nx.util.Vector2f;

/**
 * Represents a magic sphere entity
 */
public class MagicSphere extends Entity {

    private static final Image IMAGE = new Image("/assets/textures/magic2.gif");
    private static final float DEFAULT_SPEED = 30 * Game.tileSize;
    private static final double MAX_TIME_ALIVE = 4.5f;
    private static final double RADIUS = 48;
    public static final double WAIT_TIME = 1.5;

    private Vector2f direction;
    private double timeAlive = 0.0;
    private final Player player;
    private double timeSinceCreation;

    /**
     * Constructor
     * @param x Spawn position X
     * @param y Spawn position Y
     * @param player Player
     */
    public MagicSphere(double x, double y, Player player) {
        super(x, y, IMAGE);

        this.player = player;
    }

    /**
     * Updates the entity
     * @param deltaTime Frame delta
     */
    @Override
    public void update(double deltaTime) {
        timeSinceCreation += deltaTime;

        if (timeSinceCreation < WAIT_TIME)
            return;

        if (direction == null) {
            direction = new Vector2f((float) (player.getPosX() - getPosX()), (float) (player.getPosY() - getPosY())).normalize();
        }

        setPosX(getPosX() + direction.x * deltaTime * DEFAULT_SPEED);
        setPosY(getPosY() + direction.y * deltaTime * DEFAULT_SPEED);

        timeAlive += deltaTime;
        if (timeAlive > MAX_TIME_ALIVE) {
            getWorld().removeEntity(this);
            return;
        }

        if (getWorld().getLevel().isSolid((int) Math.floor(getPosX() / Game.tileSize), (int) Math.floor(getPosY() / Game.tileSize))) {
            getWorld().removeEntity(this);
            createParticleEffect(getPosX(), getPosY(), 10);
            return;
        }

        if (new Vector2f((float) getPosX(), (float) getPosY()).distance(player.getPosX(), player.getPosY()) > RADIUS)
            return;

        player.getAttacked(5);
        getWorld().removeEntity(this);

        App.mixer.addGameSound("explosion.wav").setVolume(0.04).play();

        createParticleEffect(getPosX(), getPosY(), 30);
    }

    /**
     * Creates a particle effect
     * @param posX Spawn position X
     * @param posY Spawn position Y
     * @param particleAmount Amount of particles
     */
    private void createParticleEffect(double posX, double posY, int particleAmount) {
    	//TODO No se si es esto pero cuando las particulas son creadas generan un poco de lag.
    	//cambie Random por Math.random por si pudiera ser eso.
    	// P.S cambie el tiempo de vida de las particulas y su velocidad para que se queden cerca del jugador.

        for (int i = 0; i < particleAmount; i++) {
            float directionX = randomFromInterval(-1.0f, 1.0f);
            float directionY = randomFromInterval(-1.0f, 1.0f);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), image, randomFromInterval(1.0f, 200.0f) + 100.0f));
        }
    }

}
