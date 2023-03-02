package nx.engine.world.entities;


import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.InputHandler;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.util.Vector2f;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents a bow entity
 */
public class Bow extends PickableEntity {

	private static final double SHOT_DELAY = 1;

	private double lastShot = SHOT_DELAY;

	/**
	 * Constructor
	 * @param tileset Tileset of the item
	 * @param x Spawn position X
	 * @param y Spawn position Y
	 * @param width Entity width
	 * @param height Entity height
	 */
	public Bow(TileSet tileset, double x, double y, double width, double height) {
		super(TileSetManager.loadImageFromTileSet(tileset, 128, 16, 16), x, y, width, height);
	}

	/**
	 * @return Collision shape
	 */
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}

	/**
	 * Executed when the player uses the item
	 */
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
