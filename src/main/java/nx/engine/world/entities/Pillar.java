package nx.engine.world.entities;



import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;

public class Pillar extends StaticEntity {

	public Pillar(TileSet tileset, double x, double y) {
		super(TileSetManager.loadImageFromTileSet(tileset, 25, Game.tileSize, Game.tileSize * 2), x, y,Game.tileSize,Game.tileSize * 2);
	}
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY() + (this.height/2),this.width,this.height/2);
	}
	
	@Override
	public void update(double deltaTime) {
		
		if(getPosX() + Game.tileSize > Game.player.getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Game.player.getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Game.player.getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Game.player.getCamera().getY() + Game.screenheigth) {
			if(this.checkCollision(Game.player)) {
				Game.inputHandler.ClearActiveButtons();
				Game.player.pushOut(this, Player.PLAYER_FORCE);
			}
		}
	}
	
	

}
