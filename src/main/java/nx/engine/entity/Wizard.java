package nx.engine.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;

import java.util.Set;

public class Wizard extends Entity {

    private static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");

    public Wizard() {

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, -Game.camera.getX(), -Game.camera.getY(),Game.tileSize * 1.5,Game.tileSize * 1.5);
    }

    @Override
    public Shape getCollisionShape() {
        return new Rectangle(posX + (Game.tileSize/2)/2,posY + (Game.tileSize/2),(Game.tileSize/2),(Game.tileSize/2));
    }

}
