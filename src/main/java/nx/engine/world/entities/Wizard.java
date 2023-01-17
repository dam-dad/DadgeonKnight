package nx.engine.world.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.world.Entity;

public class Wizard extends Entity {

    private static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");
    private static final double attackDelay = 2.0;

    private double timeSinceLastAttack = 0.0;

    public Wizard() {
        super(100, 120, sprite);
        height = Game.tileSize;
    }

    @Override
    public void update(double deltaTime) {
        posX += deltaTime * 48;

        timeSinceLastAttack += deltaTime;

        if (timeSinceLastAttack > attackDelay) {
            timeSinceLastAttack -= attackDelay;

            if (getWorld().getEntities().stream().anyMatch(entity -> {
                return Math.abs(entity.getPosX() - posX) < 3 * Game.tileSize && Math.abs(entity.getPosY() - posY) < 3 * Game.tileSize && entity instanceof Player;
            })) {
                System.out.println("Jugador a menos de 3 casillas");
            }
        }
    }

}
