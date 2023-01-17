package nx.engine.entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;

public class Wizard extends Entity {

    private static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");

    public Wizard() {
        super(100, 120, sprite);
    }

    @Override
    public void update(double deltaTime) {
        posX += deltaTime * 48;
    }

    @Override
    public Shape getCollisionShape() {
        return new Rectangle(posX + (Game.tileSize/2)/2,posY + (Game.tileSize/2),(Game.tileSize/2),(Game.tileSize/2));
    }

}
