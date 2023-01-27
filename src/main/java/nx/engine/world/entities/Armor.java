package nx.engine.world.entities;


import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSetManager;

public class Armor extends PickableEntity {
	
	public Armor(Image image, double x, double y, double width, double height) {
		super(TileSetManager.loadImageFromTileSet(image, 121, 16, 16), x, y, width, height);
	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}
}
