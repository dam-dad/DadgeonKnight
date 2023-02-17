package nx.engine.world.entities;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.WorldData;


public class Portal extends StaticEntity {

    private String scene;

    public Portal(double x, double y, String scene) {
        super(TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 114, Game.tileSize * 2, Game.tileSize * 2), x, y,Game.tileSize * 2,Game.tileSize * 2);
        
        this.scene = scene;
    }
    
    @Override
    public Shape getCollisionShape() {
    	return new Rectangle(getPosX(),getPosY(),this.height,this.width);
    }
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
