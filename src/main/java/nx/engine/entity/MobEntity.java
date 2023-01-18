package nx.engine.entity;

import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class MobEntity<T extends Shape> extends Entity<T> {
	
	private Player player;

	private int speed;
	private boolean hasDamage;
	private double damageValue;

	protected Direction direction;
	protected Animation animation;
	
	public double ANIMATION_SPEED = 0.15;
	public int sizeTextureX = 32;
	public int sizeTextureY = 64;
	public int scale = 1;
	
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
			gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
		}
		
	}

	@Override
	public T getCollisionShape() {
		return null;
	}

}
