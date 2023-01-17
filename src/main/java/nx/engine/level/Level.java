package nx.engine.level;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.tile.Tile;
import nx.engine.tile.TileManager;
import nx.engine.tile.TileSet;
import nx.util.CSV;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Level {

    // Map settings
    public static int maxWorldCol = 20;
    public static int maxWorldRow = 20;
    public static int worldWidth = Game.tileSize * maxWorldCol;
    public static int worldHeigth = Game.tileSize * maxWorldRow;

    private final List<Layer> layers;
    private final Layer collisionLayer;

    public Level(String... fileNames) {
        this.layers = new ArrayList<>();

        for (int i = 0; i < fileNames.length - 1; i++) {
            layers.add(new Layer(fileNames[i]));
        }

        this.collisionLayer = new Layer(fileNames[fileNames.length - 1]);
    }

    public void draw(GraphicsContext gc, Camera camera) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < maxWorldCol && worldRow < maxWorldRow) {

            int worldX = worldCol * Game.tileSize;
            int worldY = worldRow * Game.tileSize;

            double screenX = worldX - camera.getX() + Game.player.screenX;
            double screenY = worldY - camera.getY() + Game.player.screenY;

            //draw just the tiles around the player
            if(worldX + Game.tileSize > Game.player.posX - Game.screenWidth &&
                    worldX - Game.tileSize < Game.player.posX + Game.screenWidth &&
                    worldY + Game.tileSize > Game.player.posY - Game.screenheigth &&
                    worldY - Game.tileSize  < Game.player.posY + Game.screenheigth) {

                //Map base
                gc.drawImage(TileSet.tiles[layers.get(0).getTiles()[worldRow][worldCol]], screenX, screenY, Game.tileSize, Game.tileSize);
            }

            worldCol++;

            if (worldCol == maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public boolean isSolid(int x, int y) {
        return collisionLayer.getTiles()[x][y] != -1;
    }

}
