package nx.engine.world.entities;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;

/**
 * Represents an armor entity
 */
public class Armor extends PickableEntity {

	/**
	 * Constructor
	 * @param tileset Tileset
	 * @param x Position X
	 * @param y Position Y
	 */
	public Armor(TileSet tileset, double x, double y) {
		super(TileSetManager.loadImageFromTileSet(tileset, 121, 16, 16), x, y, Game.tileSize, Game.tileSize);
	}

	/**
	 * @return Entity collision shape
	 */
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}

}
