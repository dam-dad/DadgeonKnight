package nx.engine.world.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.game.App;

public class Chest extends StaticEntity {
	
	private List<Entity> inventory;
	
	private boolean closed = true;
	
	private final Image open,close;

	public Chest(double x, double y) {
		super(TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 54, Game.tileSize, Game.tileSize), x, y,Game.tileSize,Game.tileSize);
		close = TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 54, Game.tileSize, Game.tileSize);
		open = TileSetManager.loadImageFromTileSet(TileSet.DANGEON_TILES, 53, Game.tileSize, Game.tileSize);
		
		this.inventory = Arrays.asList(new Sword(TileSet.ITEMS_TILES, getPosX()/Game.tileSize, getPosY()/Game.tileSize, Game.tileSize, Game.tileSize));
		
	}
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),this.width,this.height);
	}
	@Override
	public void update(double deltaTime) {
		
		if(getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Player.get().getCamera().getY() + Game.screenheigth){
			if(Player.get().checkCollision(this)) {
				Game.inputHandler.ClearActiveButtons();
				Player.get().pushOut(this, Player.PLAYER_FORCE);
				if(closed) {
					App.mixer.addGameSound("chestOpen.mp3").play();
					this.image = open;
					closed = false;
					inventory.forEach(e -> {
						Player.get().addEntityToInventory((PickableEntity) e);
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
