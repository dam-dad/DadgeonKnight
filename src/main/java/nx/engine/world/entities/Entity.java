package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.World;
import nx.util.Direction;
import nx.util.Knockback;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class Entity {
	
	private double posX;
	private double posY;
	protected double width, height;
	protected Image image;
	private World world;

	public Entity() {
		this(0, 0);
	}

	public Entity(double posX, double posY) {
		this(posX, posY, null);
	}

	public Entity(double posX, double posY, Image image) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.image = image;
	}

	public abstract void update(double deltaTime);

	public void draw(GraphicsContext gc, Camera camera) {
		drawInternal(gc, camera, 1.0);
	}

	// TODO: Make collisions work
	protected void move(Vector2D v) {
		this.setPosX(this.getPosX() + v.getX());
		this.setPosY(this.getPosY() + v.getY());
	}
	protected void move(double x, double y) {
		this.setPosX(this.getPosX() + x);
		this.setPosY(this.getPosY() + y);
	}
	public boolean checkCollision(Entity entity) {
		if (entity.getCollisionShape() == null)
			return false;

		
		boolean collide = getCollisionShape().intersects(entity.getCollisionShape().getLayoutBounds());
		return collide;
	}

	protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX(), Game.SCREEN_CENTER_Y - camera.getY() + getPosY(), Game.tileSize * scale, Game.tileSize * scale);
	}

	public double getDistanceToEntity(Entity e) {
		return Math.sqrt(Math.pow((e.getPosX() + (e.width/2)) - (this.getPosX() + (this.width/2)), 2) + Math.pow((e.getPosY() + (e.height/2)) - (this.getPosY() + (this.height/1.5)), 2));
	}

	public double getDistanceToTile(int x, int y) {
		return Math.sqrt(Math.pow((x + (Game.tileSize/2)) - (this.getPosX() + (this.width/2)), 2) + Math.pow((y + (Game.tileSize/2)) - (this.getPosY() + (this.height/1.5)), 2));
	}

	public Vector2D getVector2DToEntity(Entity e) {
		Vector2D direction = new Vector2D((e.getPosX() + (e.width/2)) - (this.getPosX() + (this.width/2)), (e.getPosY() + (e.height/2)) - (this.getPosY() + (this.height/1.5)));
		direction = direction.normalize();

		return direction;
	}

	public Vector2D getVector2DToTile(int x, int y) {
		Vector2D direction = new Vector2D((x + (Game.tileSize/2)) - (this.getPosX() + (this.width/2)), (y + (Game.tileSize/2)) - (this.getPosY() + (this.height/1.5)));
		direction = direction.normalize();

		return direction;
	}

	public static Direction getDirectionFromVector2D(Vector2D direction) {
		double angle = Math.atan2(direction.getY(), direction.getX());
		if (angle >= -Math.PI/4 && angle < Math.PI/4) {
			return Direction.EAST;
		} else if (angle >= Math.PI/4 && angle < 3*Math.PI/4) {
			return Direction.SOUTH;
		} else if (angle >= 3*Math.PI/4 || angle < -3*Math.PI/4) {
			return Direction.WEST;
		} else {
			return Direction.NORTH;
		}
	}

	public double pushOut(Entity collition, double force) {
		Vector2D velocity = new Vector2D(0,0);
		double distance = getDistanceToEntity(collition);

		Vector2D collisionNormal = getVector2DToEntity(collition);
		Vector2D movement = new Vector2D(0,0);
		switch (getDirectionFromVector2D(collisionNormal.scalarMultiply(-1))) {
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

		move(movement);

		return distance;
	}
	public double pushOut(Entity collition, double force,Camera camera) {
		double distance = getDistanceToEntity(collition);

		Vector2D collisionNormal = getVector2DToEntity(collition);
		Vector2D movement = new Vector2D(0,0);
		switch (getDirectionFromVector2D(collisionNormal.scalarMultiply(-1))) {
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
		movement = movement.scalarMultiply(distance).scalarMultiply(force);

		move(movement);
		
		if(getClass() == Player.class)
			camera.setPosition(this.getPosX(), this.getPosY());

		return distance;
	}
	public static void knockback(Entity player,Entity collition, double force,Camera camera) {
		Knockback p = new Knockback(player, collition, force, camera);
		p.start();
	}
	public double pushOut(int x, int y, double force) {
		double distance = getDistanceToTile(x * Game.tileSize, y * Game.tileSize);

		Vector2D collisionNormal = getVector2DToTile(x * Game.tileSize, y * Game.tileSize);
		Vector2D movement = new Vector2D(0,0);
		switch (getDirectionFromVector2D(collisionNormal.scalarMultiply(-1))) {
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
		movement = movement.scalarMultiply(distance).scalarMultiply(force);
		
		move(movement);

		return distance;
	}
	public double pushOut(int x, int y, double force,Camera camera) {
		Vector2D velocity = new Vector2D(0,0);
		double distance = getDistanceToTile(x * Game.tileSize, y * Game.tileSize);

		Vector2D collisionNormal = getVector2DToTile(x * Game.tileSize, y * Game.tileSize);
		Vector2D movement = new Vector2D(0,0);
		switch (getDirectionFromVector2D(collisionNormal.scalarMultiply(-1))) {
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
		
		move(movement);
		
		if(getClass() == Player.class)
			camera.setPosition(this.getPosX(), this.getPosY());

		return distance;
	}
	
	public static float randomFromInterval(float min, float max) { // min and max included 
  	  return (float) (Math.random() * (max - min + 1) + min);
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Shape getCollisionShape() {
		return null;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public World getWorld() {
		return world;
	}

	public Image getImage() {
		return image;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

}