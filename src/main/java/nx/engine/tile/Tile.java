package nx.engine.tile;

import javafx.scene.shape.Rectangle;
import nx.engine.Game;
import nx.engine.world.entities.Entity;

@Deprecated
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

		return new Rectangle(posX * Game.tileSize - Game.tileSize, posY * Game.tileSize + Game.tileSize, Game.tileSize, Game.tileSize).intersects(entity.getCollisionShape().getLayoutBounds());
	}

	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

}
