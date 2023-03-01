package nx.engine.world.entities;

import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.game.App;

/**
 * Represents a chest entity
 */
public class Chest extends StaticEntity {
	
	private List<Entity> inventory;
	
	private boolean closed = true;
	
	private final Image open,close;

	/**
	 * Constructor
	 * @param x Spawn position X
	 * @param y Spawn position Y
	 */
	public Chest(double x, double y,Entity e) {
		super(TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 54, Game.tileSize, Game.tileSize), x, y,Game.tileSize,Game.tileSize);
		close = TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 54, Game.tileSize, Game.tileSize);
		open = TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 53, Game.tileSize, Game.tileSize);

		this.inventory = Arrays.asList(e);
	}

	public Chest(double x, double y,String inventory) {
		this(x,y,Entity.getByName(inventory, x, y));

	}

	/**
	 * @return Collision shape of the entity
	 */
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),this.width,this.height);
	}

	/**
	 * Updates the entity
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {
		if(getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Player.get().getCamera().getY() + Game.screenheigth){
			if(Player.get().checkCollision(this)) {
				Game.inputHandler.ClearActiveKeys();
				Player.get().pushOut(this, Player.PLAYER_FORCE);
				if(closed) {
					App.mixer.addGameSound("chestOpen.mp3").play();
					this.image = open;
					closed = false;
					inventory.forEach(e -> {
						Player.get().getInventory().addEntityToInventory((PickableEntity) e);
					});
				}
			}
		}
	}
	public List<Entity> getInventory() {
		return inventory;
	}
	public void setInventory(List<Entity> inventory) {
		this.inventory = inventory;
	}
	
	

}
