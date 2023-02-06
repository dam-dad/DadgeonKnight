package nx.engine.world.entities;

import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nx.engine.Camera;
import nx.engine.Game;

public class PickableEntity extends Entity {
	
	private boolean canBeSelected = true;

	public PickableEntity(Image image, double x, double y,double width,double height) {
		super(image,x * Game.tileSize,y * Game.tileSize,width,height);
	}
	public PickableEntity() {
		super(null,0,0,0,0);
	}

	@Override
	public void update(double deltaTime) {
		Optional<Player> playerOptional = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
		.map(entity -> (Player) entity).findAny();
		
		if(playerOptional.isPresent() && this.checkCollision(playerOptional.get())) {
			playerOptional.get().getInventory().add(this);
			getWorld().removeEntity(this);
			return;
		}
	}
	
	public void useItem() {
		//TODO use of the item.
	}
	
	
	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		if(getPosX() + Game.tileSize > camera.getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < camera.getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > camera.getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < camera.getY() + Game.screenheigth) {
			drawInternal(gc, camera,width,height);
		}
	}
	
	public void drawUI(GraphicsContext gc) {
		gc.setFill(Color.GOLDENROD);
		gc.fillRoundRect(8 - 4, 8 - 4, width + 8, height + 8, 8, 8);
		gc.setFill(Color.GRAY);
		gc.fillRoundRect(8 - 2, 8 - 2, width + 4, height + 4, 4, 4);
		gc.drawImage(image, 8, 8, width, height);
	}

	public boolean canBeSelected() {
		return canBeSelected;
	}

	public void setCanBeSelected(boolean canBeSelected) {
		this.canBeSelected = canBeSelected;
	}
	
	
	

}
