package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.world.Entity;
import nx.util.Vector2f;

public class Wizard extends Entity {

    private static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");
    private static final double attackDelay = 1.0;
    private static final double RADIUS = 5;

    private double timeSinceLastAttack = 0.0;

    public Wizard() {
        super(10 * Game.tileSize, 12 * Game.tileSize, sprite);
        height = Game.tileSize;
    }

    @Override
    public void update(double deltaTime) {
        timeSinceLastAttack += deltaTime;

        if (timeSinceLastAttack > attackDelay) {
            timeSinceLastAttack -= attackDelay;

            getWorld().getEntities().stream()
                    .filter(entity -> {
                        return Math.abs(entity.getPosX() - posX) < RADIUS * Game.tileSize && Math.abs(entity.getPosY() - posY) < RADIUS * Game.tileSize && entity instanceof Player;
                    })
                    .map(Player.class::cast)
                    .forEach(player -> {
                        Vector2f direction = new Vector2f((float) (player.getPosX() - posX), (float) (player.getPosY() - posY));

                        getWorld().addEntity(new Fireball(posX, posY, direction));
                    });
        }
    }

}
