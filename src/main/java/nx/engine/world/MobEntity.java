package nx.engine.world;

import nx.engine.world.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.util.Direction;

public class MobEntity extends Entity {
	
	public static final double TIME_SHOWING_ATTACK = 0.5;

	protected double speed;
	protected boolean hasDamage;
	protected double damageValue;
	
	protected boolean canDie = true;
	protected double mobHealth = 10;
	protected double timeSinceLastHit;

	protected Direction direction;
	protected Animation animation;
	
	public int sizeTextureX = Game.tileSize;
	public int sizeTextureY = Game.tileSize;
	public double scale = 1;
	
	protected int sizePlayerDetection;
	protected int sizeMobDetection;
	
	public MobEntity(double posX, double posY) {
		super(posX, posY);
	}

	@Override
	public void update(double deltaTime) {}
	
	public void getAttacked(int damage) {
		mobHealth -= canDie? damage : 0;
		timeSinceLastHit = 0;
	}

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		double screenX = Game.SCREEN_CENTER_X - camera.getX() + getPosX();
		double screenY = Game.SCREEN_CENTER_Y - camera.getY() + getPosY();

		if(getPosX() + Game.tileSize > camera.getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < camera.getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > camera.getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < camera.getY() + Game.screenheigth){
			//draw image
			if(animation == null) {
				this.drawInternal(gc, camera, scale);
				return;
			}
			
			//draw animation
//			if (timeSinceLastHit < TIME_SHOWING_ATTACK) {
//				double alpha = ((1.0 - timeSinceLastHit / TIME_SHOWING_ATTACK) * 0.2) + 0.2;
//				gc.setGlobalAlpha(alpha);
//			}
			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
//			gc.setGlobalAlpha(10);
		}

	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(), getPosY(), sizeTextureX * scale, sizeTextureY * scale);
	}

	public double getHealth() {
		return mobHealth;
	}
	

}
