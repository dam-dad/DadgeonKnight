package nx.engine.particles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.entities.Entity;
import nx.util.Vector2f;

/**
 * Represents a particle
 */
public class Particle extends Entity {

    private final Vector2f direction;
    private double timeAlive;
    private final double speed;

    /**
     * Constructor
     * @param posX Spawn position X
     * @param posY Spawn position Y
     * @param direction Particle direction
     * @param image Particle image
     * @param speed Particle speed
     */
    public Particle(float posX, float posY, Vector2f direction, Image image, double speed) {
        super(posX, posY, image);
        height = Game.tileSize * 0.25;

        this.direction = direction;
        this.speed = speed;
    }

    /**
     * Updates the particle
     * @param delta Frame delta
     */
    @Override
    public void update(double delta) {
        setPosX(getPosX() + direction.x * delta * speed);
        setPosY(getPosY() + direction.y * delta * speed);

        timeAlive += delta;
    }

    /**
     * Draws the particle
     * @param gc GraphicsContext to draw on
     * @param camera World camera
     */
    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        drawInternal(gc, camera, 0.25);
    }

    public double getTimeAlive() {
        return timeAlive;
    }

}
