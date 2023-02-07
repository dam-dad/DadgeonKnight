package nx.engine.tile;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.world.entities.Entity;


public class Tile {

	private final int id;
	private final boolean solid;

	public Tile(int id, boolean solid) {
		this.id = id;
		this.solid = solid;
	}

	public static boolean checkCollision(Entity entity, int posX, int posY) {
		if (entity.getCollisionShape() == null)
			return false;
		
		boolean collide = new Rectangle(posX * Game.tileSize, posY * Game.tileSize, Game.tileSize, Game.tileSize).intersects(entity.getCollisionShape().getLayoutBounds());
		return collide;
	}
	public static boolean checkCollision(Shape entity, int posX, int posY) {
		boolean collide = new Rectangle(posX * Game.tileSize, posY * Game.tileSize, Game.tileSize, Game.tileSize).intersects(entity.getLayoutBounds());
		return collide;
	}
	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

}
