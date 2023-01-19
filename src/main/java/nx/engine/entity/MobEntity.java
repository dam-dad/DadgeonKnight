package nx.engine.entity;

import java.util.Set;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class MobEntity<T extends Shape> extends Entity<T> {
	
	protected Player player;

	protected double speed;
	protected boolean hasDamage;
	protected double damageValue;

	protected Direction direction;
	protected Animation animation;
	
	public int sizeTextureX = 32;
	public int sizeTextureY = 64;
	public int scale = 1;
	
	protected int sizePlayerDetection = 200;
	
	public MobEntity(Player player) {
		this.player = player;
	}
	

	
	@Override
	public void update(Set<KeyCode> activeKeys, double deltaTime) {}

	@Override
	public void draw(GraphicsContext gc) {
		double screenX = posX - player.posX + player.screenX;
		double screenY = posY - player.posY + player.screenY;
		
		if(posX + Game.tileSize > player.posX - Game.screenWidth && 
				posX - Game.tileSize < player.posX + Game.screenWidth &&
				posY + Game.tileSize > player.posY - Game.screenheigth && 
				posY - Game.tileSize  < player.posY + Game.screenheigth){
			
			gc.fillRect(screenX, screenY, sizeTextureX * scale, sizeTextureY * scale);
			gc.setFill(Color.WHEAT);
			gc.fillOval(screenX + ((sizeTextureX * scale)/2) - (sizePlayerDetection * 2)/2, screenY + ((sizeTextureY * scale)/2) - (sizePlayerDetection * 2)/2 , sizePlayerDetection * 2, sizePlayerDetection * 2);
			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
		}
		
	}
	


	@Override
	public T getCollisionShape() {
		return null;
	}

}
