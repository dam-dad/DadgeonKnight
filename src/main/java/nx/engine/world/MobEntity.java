package nx.engine.world;

import nx.engine.world.entities.Entity;

import nx.engine.Animation;
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

//	@Override
//	public void draw(GraphicsContext gc, Camera camera) {
//		double screenX = camera.getX();
//		double screenY = camera.getY();
//
//		if(posX + Game.tileSize > player.posX - Game.screenWidth &&
//				posX - Game.tileSize < player.posX + Game.screenWidth &&
//				posY + Game.tileSize > player.posY - Game.screenheigth &&
//				posY - Game.tileSize  < player.posY + Game.screenheigth){
//
//			gc.fillRect(screenX, screenY, sizeTextureX * scale, sizeTextureY * scale);
//			gc.setFill(Color.WHEAT);
//			gc.fillOval(screenX + ((sizeTextureX * scale)/2) - (sizePlayerDetection * 2)/2, screenY + ((sizeTextureY * scale)/2) - (sizePlayerDetection * 2)/2 , sizePlayerDetection * 2, sizePlayerDetection * 2);
//			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
//		}
//
//	}

}
