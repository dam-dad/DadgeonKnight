package nx.engine.particles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.entities.Entity;
import nx.util.Vector2f;

public class Particle extends Entity {

    private Vector2f direction;
    private double timeAlive;
    private double speed;

    public Particle(float posX, float posY, Vector2f direction, Image image, double speed) {
        super(posX, posY, image);
        height = Game.tileSize * 0.25;

        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void update(double delta) {
        setPosX(getPosX() + direction.x * delta * speed);
        setPosY(getPosY() + direction.y * delta * speed);

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
