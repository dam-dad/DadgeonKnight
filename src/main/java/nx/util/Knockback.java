package nx.util;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.concurrent.Task;
import nx.engine.tile.Tile;
import nx.engine.world.Level;
import nx.engine.world.entities.Entity;
import nx.engine.world.entities.Player;

/**
 * Represents a knockback effect
 */
public class Knockback extends Task<Void> {
	
	private Entity player;
	private Entity collision;
	
	
	private double knockbackSpeed;
	private double knockbackDuration;
	
	Level level;
	int levelWidth;
	int levelHeight;

	/**
	 * Constructor
	 * @param player Player to knock
	 * @param collision Collision
	 * @param knockbackForce Force of the effect
	 * @param knockbackDuration Duration of the effect
	 */
	public Knockback(Entity player, Entity collision, double knockbackForce, double knockbackDuration) {
		this.collision = collision;
		this.player = player;
		this.knockbackSpeed = knockbackForce;
		this.knockbackDuration = knockbackDuration;
		
		level = player.getWorld().getLevel();
		levelWidth = level.getLayers().get(0).getLayerWidth();
		levelHeight = level.getLayers().get(0).getLayerHeight();
	}

	/**
	 * Returns the direction between entities
	 * @param player Player
	 * @param e Entity
	 * @return Direction vector between specified entities
	 */
	public Vector2D getVector2DeBetweenEntity(Entity player,Entity e) {
		Vector2D direction = new Vector2D((e.getPosX() + (e.getWidth()/2)) - (player.getPosX() + (player.getWidth()/2)), (e.getPosY() + (e.getHeight()/2)) - (player.getPosY() + (player.getHeight()/1.5)));
		direction = direction.normalize();

		return direction;
	}

	/**
	 * Moves the player
	 * @param move Movement vector
	 * @throws InterruptedException
	 */
	protected void move(Vector2D move) throws InterruptedException {
		player.setPosX(player.getPosX() + move.getX());
		player.setPosY(player.getPosY() + move.getY());
		
		Player.get().getCamera().setPosition(player.getPosX() + move.getX(), player.getPosY() + move.getY());
	}

	/**
	 * Constraints a value between two values
	 * @param a Value to constraint
	 * @param b First value
	 * @param t Second value
	 * @return
	 */
	public static double lerp(double a, double b, double t) {
	    return a + (b - a) * t;
	}

	/**
	 * Executes the knockback effect
	 * @return
	 * @throws Exception
	 */
	@Override
	protected Void call() throws Exception {
		long lastTime = System.nanoTime();
		double time = 0.0;
		double delta = 0;
		double currentSpeed = 0.4;

		Vector2D collisionNormal = getVector2DeBetweenEntity(player,collision);
		Vector2D movement = new Vector2D(0,0);
		switch (Entity.getDirectionFromVector2D(collisionNormal.scalarMultiply(-1))) {
			case WEST:
				movement = new Vector2D(-1,0);
				break;
			case EAST:
				movement = new Vector2D(1,0);
				break;
			case NORTH:
				movement = new Vector2D(0,-1);
				break;
			case SOUTH:
				movement = new Vector2D(0,1);
				break;
			default:
				break;
		}
		movement = movement.scalarMultiply(100);
		
		while (time < knockbackDuration) {
			long currentTime = System.nanoTime();
			double deltaTime = (currentTime - lastTime) / 1000000000.0;
			delta += (currentTime - lastTime) / (1000000000.0 / 60);
			lastTime = currentTime;
			

			
			if (delta >= 1) {
				currentSpeed = lerp(currentSpeed,0.2,0.1);
				
				
				movement = movement.scalarMultiply(currentSpeed);
				
				move(movement);
				delta--;
			}
			
			time += deltaTime;
		}
		
		return null;
	}
}