package nx.engine.particles;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.world.entities.Entity;
import nx.engine.world.World;

import java.util.Iterator;
import java.util.Random;

public class ParticleManager {

    private static final double MAX_TIME_ALIVE = 0.5;
    private static final float PARTICLE_SPEED = 2.5f * Game.tileSize;
    private static final float DELAY = 0.0625f;

    private final World world;
    private final Image image;

    private double timeSinceLastParticle = 0;

    private final Random random;

    public ParticleManager(World world, String image) {
        this.world = world;
        this.image = new Image(image);

        this.random = new Random();
    }

    public void update(double delta) {
        Iterator<Entity> iterator = world.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            if (!(entity instanceof Particle))
                continue;

            Particle particle = (Particle) entity;

            if (particle.getTimeAlive() > MAX_TIME_ALIVE)
                iterator.remove();
        }
    }

}
