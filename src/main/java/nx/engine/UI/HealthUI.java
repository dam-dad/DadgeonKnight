package nx.engine.UI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.world.entities.Player;

public class HealthUI implements UI {

    private static final Image heart = new Image("/assets/textures/ui/heart.png");
    private static final Image half_heart = new Image("/assets/textures/ui/half_heart.png");

    private Player player;

    public HealthUI(Player player) {
        this.player = player;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        for (int i = 0; i < player.getHealth() / 2; i++) {
            gc.drawImage(heart, 8 + i * (48 + 8), 8, 48, 48);
        }

        if (player.getHealth() % 2 != 0) {
            gc.drawImage(half_heart, 8 + (player.getHealth() / 2) * (48 + 8), 8, 48, 48);
        }
    }

}
