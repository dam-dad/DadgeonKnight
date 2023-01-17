package nx.engine.particles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.Entity;
import nx.util.Vector2f;

public class Particle extends Entity {

    private Vector2f direction;
    private double timeAlive;

    public Particle(float posX, float posY, Vector2f direction, Image image) {
        super(posX, posY, image);
        height = Game.tileSize * 0.25;

        this.direction = direction;
    }

    @Override
    public void update(double delta) {
        posX += direction.x * delta;
        posY += direction.y * delta;

        timeAlive += delta;
    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        drawInternal(gc, camera, 0.25);
    }

    public double getTimeAlive() {
        return timeAlive;
    }

}
