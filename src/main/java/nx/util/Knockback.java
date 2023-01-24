package nx.util;

import java.util.Iterator;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.tile.Tile;
import nx.engine.world.Level;
import nx.engine.world.entities.Entity;

public class Knockback extends Thread {
	
	public static boolean isBeingUsed = false;
	
	private Entity player;
	private Entity collition;
	private double force;
	private Camera camera;
	
	private double friction = 0.005;
	
	Level level;
	int levelWidth;
	int levelHeight;
	
	public Knockback(Entity player,Entity collition,double force,Camera camera) {
		this.collition = collition;
		this.force = force;
		this.camera = camera;
		this.player = player;
		
		level = player.getWorld().getLevel();
		levelWidth = level.getLayers().get(0).getLayerWidth();
		levelHeight = level.getLayers().get(0).getLayerHeight();
		
		
	}
	public double getDistanceBetweenEntity(Entity player,Entity e) {
		return Math.sqrt(Math.pow((e.getPosX() + (e.getWidth()/2)) - (player.getPosX() + (player.getWidth()/2)), 2) + Math.pow((e.getPosY() + (e.getHeight()/2)) - (player.getPosY() + (player.getHeight()/1.5)), 2));
	}
	public Vector2D getVector2DeBetweenEntity(Entity player,Entity e) {
		Vector2D direction = new Vector2D((e.getPosX() + (e.getWidth()/2)) - (player.getPosX() + (player.getWidth()/2)), (e.getPosY() + (e.getHeight()/2)) - (player.getPosY() + (player.getHeight()/1.5)));
		direction = direction.normalize();

		return direction;
	}
	
	@Override
	public void run() {
		if(isBeingUsed)
			return;
		
		isBeingUsed = true;
		
		while(force > 0) {
			for (int i = 0; i < levelHeight; i++) {
				for (int j = 0; j < levelWidth; j++) {
					if(level.isSolid(i, j) && Tile.checkCollision(player, i, j)) {
						return;
					}
				}
			}

			Vector2D velocity = new Vector2D(0,0);
			double distance = getDistanceBetweenEntity(player,collition);

			Vector2D collisionNormal = getVector2DeBetweenEntity(player,collition);
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
			movement = movement.scalarMultiply(distance).scalarMultiply(force).add(velocity);
			
			velocity = movement;
			
			try {
				move(movement);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			camera.setPosition(player.getPosX(), player.getPosY());
			force -= friction;
		}

		isBeingUsed = false;
	}
	
	protected void move(Vector2D move) throws InterruptedException {
		player.setPosX(player.getPosX() + move.getX());
		player.setPosY(player.getPosY() + move.getY());
		
		Thread.sleep(10);
	}

}
