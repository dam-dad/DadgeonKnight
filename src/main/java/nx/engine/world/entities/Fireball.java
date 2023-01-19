package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.particles.Particle;
import nx.util.Music;
import nx.util.Vector2f;

import java.util.Optional;
import java.util.Random;

public class Fireball extends Entity {

    private static final Image IMAGE = new Image("/assets/textures/bola_du_fogo.gif");
    private static final float SPEED = 3.5f * Game.tileSize;
    private static final double MAX_TIME_ALIVE = 2.5f;
    private static final double RADIUS = 48;

    private final Vector2f direction;
    private double timeAlive = 0.0;

    public Fireball(double x, double y, Vector2f direction) {
        super(x, y, IMAGE);

        this.direction = direction.normalize();
    }

    @Override
    public void update(double deltaTime) {
        posX += direction.x * deltaTime * SPEED;
        posY += direction.y * deltaTime * SPEED;

        timeAlive += deltaTime;
        if (timeAlive > MAX_TIME_ALIVE) {
            getWorld().removeEntity(this);
            return;
        }

        Optional<Player> playerOptional = getWorld().getEntities().stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .findAny();

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            if (new Vector2f((float) posX, (float) posY).distance(player.getPosX(), player.getPosY()) > RADIUS)
                return;

            player.getAttacked(3);
            getWorld().removeEntity(this);

            Music music = new Music("explosion.wav");
            music.play();

            createParticleEffect(posX, posY);
        }
    }

    private void createParticleEffect(double posX, double posY) {
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            float directionX = (random.nextFloat(2) - 1);
            float directionY = (random.nextFloat(2) - 1);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), image, random.nextFloat(200) + 300));
        }
    }

}
