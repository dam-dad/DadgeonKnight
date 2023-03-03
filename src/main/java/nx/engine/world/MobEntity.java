package nx.engine.world;

import nx.engine.world.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.util.Direction;

/**
 * Represents a mob, with animation, health and movement
 */
public class MobEntity extends Entity {
	
	public static final double TIME_SHOWING_ATTACK = 0.5;

	protected double speed;
	protected boolean hasDamage;
	
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

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 */
	public MobEntity(double posX, double posY) {
		super(posX, posY);
	}

	/**
	 * Updates the MobEntity
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {}

	/**
	 * Subtract health to the mob
	 * @param damage Damage to deal
	 */
	public void getAttacked(int damage) {
		mobHealth -= canDie? damage : 0;
		timeSinceLastHit = 0;
	}

	/**
	 * Draws the entity
	 * @param gc GraphicsContext to draw on
	 * @param camera World camera
	 */
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
			gc.save();
			if (canDie && timeSinceLastHit < TIME_SHOWING_ATTACK) {
				double alpha = ((1.0 - timeSinceLastHit / TIME_SHOWING_ATTACK) * 0.2) + 0.2;
				gc.setGlobalAlpha(alpha);
			}
			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
//			gc.fillRect(screenX, screenY + (sizeTextureY * scale) - Game.tileSize, sizeTextureX * scale, Game.tileSize);
			
			gc.restore();
		}

	}

	/**
	 * @return Collision shape for the MobEntity
	 */
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(), getPosY(), sizeTextureX * scale, sizeTextureY * scale);
	}

}
