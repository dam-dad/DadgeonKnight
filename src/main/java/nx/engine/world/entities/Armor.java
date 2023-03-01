package nx.engine.world.entities;


import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.Effect;

public class Armor extends PickableEntity {
	
	public Armor(TileSet tileset, double x, double y,String effect) {
		super(TileSetManager.loadImageFromTileSet(tileset, 121, 16, 16), x, y, Game.tileSize, Game.tileSize);
		this.canBeSelected = false;
		this.setEffect(Effect.valueOf(effect));
	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}
}
