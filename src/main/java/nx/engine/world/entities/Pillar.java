package nx.engine.world.entities;

import java.util.Optional;

import javafx.scene.image.Image;
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
		Optional<Player> playerOptional = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
		.map(entity -> (Player) entity).findAny();
		
		if(playerOptional.isPresent() && this.checkCollision(playerOptional.get())) {
			Game.inputHandler.ClearActiveKeys();
			playerOptional.get().pushOut(this, Player.PLAYER_FORCE);
		}
	}
	
	

}
