package nx.engine.world;

import nx.engine.world.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.util.Direction;

public class MobEntity extends Entity {

	protected double speed;
	protected boolean hasDamage;
	protected double damageValue;
	
	protected boolean canDie = true;
	protected double mobHealth = 5;

	protected Direction direction;
	protected Animation animation;
	
	public int sizeTextureX = Game.tileSize;
	public int sizeTextureY = Game.tileSize;
	public int scale = 1;
	
	protected int sizePlayerDetection;
	
	public MobEntity(double posX, double posY) {
		super(posX, posY);
	}

	@Override
	public void update(double deltaTime) {}
	
	public void getAttacked(int damage) {
		mobHealth -= canDie? damage : 0;
	}

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		double screenX = Game.SCREEN_CENTER_X - camera.getX() + getPosX();
		double screenY = Game.SCREEN_CENTER_Y - camera.getY() + getPosY();

		if(getPosX() + Game.tileSize > camera.getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < camera.getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > camera.getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < camera.getY() + Game.screenheigth){
			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
		}

	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(), getPosY(), sizeTextureX * scale, sizeTextureY * scale);
	}
	

}
