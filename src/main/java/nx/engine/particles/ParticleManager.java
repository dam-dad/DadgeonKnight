package nx.engine.particles;

import javafx.scene.image.Image;
import nx.engine.world.entities.Entity;
import nx.engine.world.World;

import java.util.Iterator;

/**
 * Manages the particles on a world
 */
public class ParticleManager {
	
	public static Image smoke = new Image("/assets/textures/items/smoke.gif");

    private static final double MAX_TIME_ALIVE = 0.5;

    private final World world;

    /**
     * Constructor
     * @param world World the particles are in
     * @param image @deprecated
     */
    public ParticleManager(World world, String image) {
        this.world = world;
    }

    /**
     * Updates the particle manager
     * @param delta Frame delta time
     */
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
