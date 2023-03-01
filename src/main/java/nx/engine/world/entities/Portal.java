package nx.engine.world.entities;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.WorldData;

/**
 * Represents a portal entity
 */
public class Portal extends StaticEntity {

    private String scene;

    /**
     * Constructor
     * @param x Spawn tile X position
     * @param y Spawn tile Y position
     * @param scene Scene to teleport the player to
     */
    public Portal(double x, double y, String scene) {
        super(TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 114, Game.tileSize * 2, Game.tileSize * 2), x, y,Game.tileSize * 2,Game.tileSize * 2);
        
        this.scene = scene;
    }

    /**
     * Returns the collision shape
     * @return Portal collision shape
     */
    @Override
    public Shape getCollisionShape() {
    	return new Rectangle(getPosX(),getPosY(),this.height,this.width);
    }

    /**
     * Updates the portal, and teleports the player if it is touching it
     * @param deltaTime Frame delta
     */
    @Override
    public void update(double deltaTime) {
		if(getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Player.get().getCamera().getY() + Game.screenheigth) {
	    	if(Player.get().checkCollision(this)) {
	    		Game.changeScene(new WorldScene(WorldData.getByName(scene)));
	    	}
		}
    }

}
