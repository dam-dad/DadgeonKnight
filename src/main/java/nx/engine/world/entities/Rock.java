package nx.engine.world.entities;

import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;

/**
 * Represents a rock entity
 */
public class Rock extends StaticEntity {

	/**
	 * Constructor
	 * @param tileset Tileset to load the texture from
	 * @param x Spawn position X
	 * @param y Spawn position Y
	 * @param width Entity width
	 * @param height Entity height
	 */
	public Rock(TileSet tileset, double x, double y, double width, double height) {
		super(TileSetManager.loadImageFromTileSet(tileset, 14, Game.tileSize, Game.tileSize), x, y, width, height);
	}

	/**
	 * Returns the collision shape
	 * @return Rock collision shape
	 */
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),this.width,this.height);
	}

	/**
	 * Updates the rock
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {
		Optional<Player> playerOptional = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
		.map(entity -> (Player) entity).findAny();
		
		if(playerOptional.isPresent() && this.checkCollision(playerOptional.get())) {
			Game.inputHandler.ClearActiveKeys();
			Entity.knockback(this, playerOptional.get(), 0.7, 1.0);
		}
	}

}
