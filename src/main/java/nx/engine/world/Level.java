package nx.engine.world;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a level
 */
public class Level {

    // Map settings
    private int maxWorldCol = 20;
    private int maxWorldRow = 20;
    private int worldWidth = Game.tileSize * maxWorldCol;
    private int worldHeigth = Game.tileSize * maxWorldRow;

    private final List<Layer> layers;
    private final Layer collisionLayer;

    private final int levelWidth;
    private final int levelHeight;

    private final TileSet tileSet;

    /**
     * Constructor
     * @param tileSet Tileset the level has
     * @param layers Layers of the level
     */
    public Level(TileSet tileSet, Layer... layers) {
        this.tileSet = tileSet;
        this.layers = new ArrayList<>();

        for (int i = 0; i < layers.length - 1; i++) {
            Layer layer = layers[i];
            layer.setLevel(this);
            this.layers.add(layer);
        }

        this.collisionLayer = layers[layers.length - 1];
        collisionLayer.setLevel(this);

        this.levelWidth = collisionLayer.getLayerWidth();
        this.levelHeight = collisionLayer.getLayerHeight();
        
        setMapSize(levelWidth,levelHeight);
    }

    /**
     * Draws the level
     * @param gc GraphicsContext to draw on
     * @param camera World camera
     */
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
            		if(layers.get(i).getTilesValues()[worldCol][worldRow] != -1)
            			gc.drawImage(tileSet.getTiles()[layers.get(i).getTilesValues()[worldCol][worldRow]], Game.SCREEN_CENTER_X - camera.getX() + worldX, Game.SCREEN_CENTER_Y - camera.getY() + worldY, Game.tileSize, Game.tileSize);
            	}
            	
//            	displayCollisions(gc,camera,worldCol,worldRow,worldX,worldY);
            }

            worldCol++;

            if (worldCol == maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    /**
     * Displays the collisions
     * @param gc GraphicsContext to draw on
     * @param camera World camera
     * @param worldCol World width
     * @param worldRow World height
     * @param worldX World position X
     * @param worldY World position Y
     */
    private void displayCollisions(GraphicsContext gc, Camera camera,int worldCol,int worldRow,int worldX,int worldY) {
    	if(isSolid(worldCol, worldRow)) {
        	gc.setFill(Color.BLUE);
        	gc.fillRect(Game.SCREEN_CENTER_X - camera.getX() + worldX,Game.SCREEN_CENTER_Y - camera.getY() + worldY, Game.tileSize, Game.tileSize);
    	}
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public Layer getCollisionLayer() {
        return collisionLayer;
    }

    /**
     * Returns if a tile is solid
     * @param x Tile position X
     * @param y Tile position Y
     * @return True if tile is solid or the position is invalid, false if not
     */
    public boolean isSolid(int x, int y) {
        if (x < 0 || x >= levelWidth) return true;
        if (y < 0 || y >= levelHeight) return true;
        return collisionLayer.getTilesValues()[x][y] != -1;
    }

    public void setMapSize(int col,int row) {
    	this.maxWorldCol = col;
    	this.maxWorldRow = row;
    	this.worldWidth = Game.tileSize * maxWorldCol;
        this.worldHeigth = Game.tileSize * maxWorldRow;
    }

}
