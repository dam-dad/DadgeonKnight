package nx.engine.world;

import nx.engine.world.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.util.Direction;

public class MobEntity extends Entity {

	protected double speed;
	protected boolean hasDamage;
	protected double damageValue;

	protected Direction direction;
	protected Animation animation;
	
	public int sizeTextureX = 32;
	public int sizeTextureY = 64;
	public int scale = 1;
	
	protected int sizePlayerDetection = 200;
	
	public MobEntity(double posX, double posY) {
		super(posX, posY);
	}

	@Override
	public void update(double deltaTime) {

	}

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		double screenX = camera.getX();
		double screenY = camera.getY();

		if(posX + Game.tileSize > camera.getX() - Game.screenWidth &&
				posX - Game.tileSize < camera.getX() + Game.screenWidth &&
				posY + Game.tileSize > camera.getY() - Game.screenheigth &&
				posY - Game.tileSize  < camera.getY() + Game.screenheigth){

			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
		}

	}

}
