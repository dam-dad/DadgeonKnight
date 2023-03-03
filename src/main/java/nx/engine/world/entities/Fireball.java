package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.particles.Particle;
import nx.game.App;
import nx.util.Vector2f;

import java.util.Optional;

/**
 * Represents a fireball entity
 */
public class Fireball extends Entity {

    private static final Image IMAGE = new Image("/assets/textures/bola_du_fogo.gif");
    private static final float DEFAULT_SPEED = 3.5f * Game.tileSize;
    private static final double MAX_TIME_ALIVE = 2.5f;
    private static final double RADIUS = 48;

    private final Vector2f direction;
    private final float speed;
    private double timeAlive = 0.0;

    /**
     * Constructor
     * @param x Spawn position X
     * @param y Spawn position Y
     * @param direction Direction
     */
    public Fireball(double x, double y, Vector2f direction) {
        this(x, y, direction, DEFAULT_SPEED);
    }

    /**
     * Constructor
     * @param x Spawn position X
     * @param y Spawn position Y
     * @param direction Direction
     * @param speed Speed
     */
    public Fireball(double x, double y, Vector2f direction, float speed) {
        super(x, y, IMAGE);

        this.speed = speed;
        this.direction = direction.normalize();
    }

    /**
     * Updates the entity
     * @param deltaTime Frame delta
     */
    @Override
    public void update(double deltaTime) {
        setPosX(getPosX() + direction.x * deltaTime * speed);
        setPosY(getPosY() + direction.y * deltaTime * speed);

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

        Optional<Player> playerOptional = getWorld().getEntities().stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .findAny();

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            if (new Vector2f((float) getPosX(), (float) getPosY()).distance(player.getPosX(), player.getPosY()) > RADIUS)
                return;

            player.getAttacked(2);
            getWorld().removeEntity(this);

            App.mixer.addGameSound("explosion.wav").setVolume(0.04).play();

            createParticleEffect(getPosX(), getPosY(), 30);
        }
    }

    /**
     * Creates a particle effect
     * @param posX Spawn position X
     * @param posY Spawn position Y
     * @param particleAmount
     */
    private void createParticleEffect(double posX, double posY, int particleAmount) {
        for (int i = 0; i < particleAmount; i++) {
            float directionX = randomFromInterval(-1.0f, 1.0f);
            float directionY = randomFromInterval(-1.0f, 1.0f);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), image, randomFromInterval(1.0f, 200.0f) + 100.0f));
        }
    }

}
