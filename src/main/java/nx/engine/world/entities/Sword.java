package nx.engine.world.entities;

import java.util.Optional;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.tile.TileSetManager;

public class Sword extends StaticEntity {
	
	private boolean isOnPlayer = false;
	
	public Sword(Image image, double x, double y, double width, double height) {
		super(TileSetManager.loadImageFromTileSet(image, 91, 16, 16), x, y, width, height);
	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}


	@Override
	public void update(double deltaTime) {
		Optional<Player> playerOptional = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
		.map(entity -> (Player) entity).findAny();
		if(isOnPlayer) {
			Player player = playerOptional.get();
			Set<MouseButton> activeKeys = Game.inputHandler.getActiveButtons();
			
			if(activeKeys.contains(MouseButton.PRIMARY)) {
				switch (player.getDirection()) {
				case WEST:
					
					break;
				case EAST:
					
					break;
				case NORTH:
					
					break;
				case SOUTH:
					
					break;
				default:
					break;
				}
			}
			
			setPosition(player.getPosX(), player.getPosY());
			
			
			return;
		}
		
		if(playerOptional.isPresent() && this.checkCollision(playerOptional.get())) {
			playerOptional.get().getInventory().add(this);
			isOnPlayer = true;
		}
	}
	
	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		if(getPosX() + Game.tileSize > camera.getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < camera.getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > camera.getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < camera.getY() + Game.screenheigth) {
//			if(!isOnPlayer)
				drawInternal(gc, camera,width,height);
		}
	}
	
	public boolean isOnPlayer() {
		return isOnPlayer;
	}

}
