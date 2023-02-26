package nx.engine.world.entities;


import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.InputHandler;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.util.Vector2f;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Bow extends PickableEntity {

	
	private static final double SHOT_DELAY = 0.8;

	private double lastShot = SHOT_DELAY;

	public Bow(TileSet tileset, double x, double y, double width, double height) {
		super(TileSetManager.loadImageFromTileSet(tileset, 128, 16, 16), x, y, width, height);
	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}
	
	@Override
	public void useItem() {
		if ((System.currentTimeMillis() - lastShot) / 1000 < SHOT_DELAY)
			return;

		lastShot = System.currentTimeMillis();

		Vector2D direction = new Vector2D(InputHandler.posX - Game.screenWidth/2, InputHandler.posY - Game.screenheigth/2);
		direction = direction.normalize();
		
		
		Player.get().getWorld().addEntity(new Arrow(Player.get().getPosX(), Player.get().getPosY(), new Vector2f((float) direction.getX(), (float) direction.getY())));

	}

}
