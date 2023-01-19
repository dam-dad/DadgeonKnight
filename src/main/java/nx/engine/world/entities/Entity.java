package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.World;
import nx.util.Direction;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class Entity {
	
	protected double posX, posY;
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
		this.posX = posX;
		this.posY = posY;
		this.image = image;
	}

	public abstract void update(double deltaTime);

	public void draw(GraphicsContext gc, Camera camera) {
		drawInternal(gc, camera, 1.0);
	}

	// TODO: Make collisions work
	protected void move(double x, double y) {
		this.posX += x;
		this.posY += y;
	}

	protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + posX, Game.SCREEN_CENTER_Y - camera.getY() + posY, Game.tileSize * scale, Game.tileSize * scale);
	}

	public double getDistanceToEntity(Entity e) {
		return Math.sqrt(Math.pow((e.posX + (e.width/2)) - (this.posX + (this.width/2)), 2) + Math.pow((e.posY + (e.height/2)) - (this.posY + (this.height/1.5)), 2));
	}

	public double getDistanceToTile(int x, int y) {
		return Math.sqrt(Math.pow((x + (Game.tileSize/2)) - (this.posX + (this.width/2)), 2) + Math.pow((y + (Game.tileSize/2)) - (this.posY + (this.height/1.5)), 2));
	}

	public Vector2D getVector2DToEntity(Entity e) {
		Vector2D direction = new Vector2D((e.posX + (e.width/2)) - (this.posX + (this.width/2)), (e.posY + (e.height/2)) - (this.posY + (this.height/1.5)));
		direction = direction.normalize();

		return direction;
	}

	public Vector2D getVector2DToTile(int x, int y) {
		Vector2D direction = new Vector2D((x + (Game.tileSize/2)) - (this.posX + (this.width/2)), (y + (Game.tileSize/2)) - (this.posY + (this.height/1.5)));
		direction = direction.normalize();

		return direction;
	}

	public Direction getDirectionFromVector2D(Vector2D direction) {
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

		this.posX += movement.getX();
		this.posY += movement.getY();

		return distance;
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

		this.posX += movement.getX();
		this.posY += movement.getY();

		return distance;
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

}
