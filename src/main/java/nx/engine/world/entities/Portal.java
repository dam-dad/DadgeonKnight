package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.scenes.Scene;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;

import java.util.List;
import java.util.stream.Collectors;

public class Portal extends Entity {

    private static final double DISTANCE = 48;

    private static final Image image = TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 114, Game.tileSize*2,Game.tileSize*2);

    private final Scene scene;

    public Portal(double x, double y, Scene scene) {
        super(image,x * Game.tileSize, y * Game.tileSize,Game.tileSize * 2,Game.tileSize * 2);

        this.scene = scene;
    }

    @Override
    public void update(double deltaTime) {
        List<Player> playerList = getWorld().getEntities().stream()
                .filter(entity -> getDistanceToEntity(entity) < DISTANCE)
                .filter(entity -> entity instanceof Player)
                .map(Player.class::cast)
                .collect(Collectors.toList());

        if (playerList.size() > 0) {
            Game.changeScene(scene);
        }
    }

}
