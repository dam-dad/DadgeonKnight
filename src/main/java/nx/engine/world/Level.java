package nx.engine.world;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.tile.TileSet;

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

            //draw just the tiles around the player
            if(worldX + Game.tileSize > camera.getX() - Game.screenWidth &&
                    worldX - Game.tileSize < camera.getX() + Game.screenWidth &&
                    worldY + Game.tileSize > camera.getY() - Game.screenheigth &&
                    worldY - Game.tileSize  < camera.getY() + Game.screenheigth) {

                //Map base

            	for(int i = 0; i < layers.size(); i++) {
            		gc.drawImage(TileSet.tiles[layers.get(i).getTiles()[worldCol][worldRow]], Game.SCREEN_CENTER_X - camera.getX() + worldX, Game.SCREEN_CENTER_Y - camera.getY() + worldY, Game.tileSize, Game.tileSize);
            	}
            	
//            	if(isSolid(worldCol, worldRow)) {
//                	gc.setFill(Color.BLUE);
//                	gc.fillRect(Game.SCREEN_CENTER_X - camera.getX() + worldX,Game.SCREEN_CENTER_Y - camera.getY() + worldY, Game.tileSize, Game.tileSize);
//            	}

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
