package nx.engine.world.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.InputHandler;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.MobEntity;
import nx.util.Vector2f;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.Optional;

public class Bow extends PickableEntity {

	private static final double ANIMATION_SPEED = 0.15;
	private static final double SHOT_DELAY = 0.5;

	private static int SwordDamage = 4;

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

		Player player = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
		.map(entity -> (Player) entity).findAny().get();

		Vector2D direction = new Vector2D(InputHandler.posX - Game.screenWidth/2, InputHandler.posY - Game.screenheigth/2);
		direction = direction.normalize();

		getWorld().addEntity(new Arrow(player.getPosX(), player.getPosY(), new Vector2f((float) direction.getX(), (float) direction.getY())));
	}

}
