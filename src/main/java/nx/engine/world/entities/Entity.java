package nx.engine.world.entities;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.world.World;
import nx.engine.world.WorldData;
import nx.util.CSV;
import nx.util.Direction;
import nx.util.Knockback;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents an entity (enemy, player, item on the ground...)
 */
public abstract class Entity {
	
	protected double posX;
	protected double posY;
	protected double width, height;
	protected Image image;
	private World world;

	/**
	 * Constructor
	 */
	public Entity() {
		this(0, 0);
	}

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 */
	public Entity(double posX, double posY) {
		this(posX, posY, null);
	}

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 * @param image Entity sprite
	 */
	public Entity(double posX, double posY, Image image) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.image = image;
	}

	/**
	 * Constructor
	 * @param image Entity image
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 * @param width Entity width
	 * @param height Entity height
	 */
	public Entity(Image image,double posX, double posY,double width,double height) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.image = image;
		this.width = width;
		this.height = height;
	}

	/**
	 * Updates the entity
	 * @param deltaTime Frame delta
	 */
	public abstract void update(double deltaTime);

	/**
	 * Draws the entity using a scale of 1.0
	 * @param gc GraphicsContext to draw on
	 * @param camera World camera
	 */
	public void draw(GraphicsContext gc, Camera camera) {
		drawInternal(gc, camera, 1.0);
	}

	/**
	 * Move the entity the specified offset
	 * @param v Offset vector
	 */
	protected void move(Vector2D v) {
		this.setPosX(this.getPosX() + v.getX());
		this.setPosY(this.getPosY() + v.getY());
	}

	/**
	 * Move the entity the specified offset
	 * @param x X offset
	 * @param y Y offset
	 */
	protected void move(double x, double y) {
		this.setPosX(this.getPosX() + x);
		this.setPosY(this.getPosY() + y);
	}

	/**
	 * Sets the entity position to the specified coordinates
	 * @param x X position
	 * @param y Y position
	 */
	protected void setPosition(double x, double y) {
		this.setPosX(x);
		this.setPosY(y);
	}

	/**
	 * Load entities from a string
	 * @param str CSV data
	 * @return List of entities loaded
	 */
	public static List<Entity> loadEntititiesFromCSV(String str) {
		try {
			List<String[]> a = CSV.readAllLines(Paths.get(CSV.class.getResource(str).toURI()));
			List<Entity> toReturn  = new ArrayList<Entity>();
			a.forEach(e -> {
				switch (e[0].toLowerCase()) {
					case "orc":
						toReturn.add(new Orc(Double.parseDouble(e[1]), Double.parseDouble(e[2]), Double.parseDouble(e[3]), Double.parseDouble(e[4])));
						break;
					case "wizard":
						toReturn.add(new Wizard(Double.parseDouble(e[1]), Double.parseDouble(e[2])));
						break;
					case "armor":
						toReturn.add(new Armor(TileSet.ITEMS_TILES, Double.parseDouble(e[1]), Double.parseDouble(e[2]),e[3]));
						break;
					case "sword":
						toReturn.add(new Sword(TileSet.ITEMS_TILES, Double.parseDouble(e[1]), Double.parseDouble(e[2]), Game.tileSize, Game.tileSize));
						break;
					case "bow":
						toReturn.add(new Bow(TileSet.ITEMS_TILES, Double.parseDouble(e[1]), Double.parseDouble(e[2]), Game.tileSize, Game.tileSize));
						break;
					case "pillar":
						toReturn.add(new Pillar(TileSet.DANGEON_TILES, Double.parseDouble(e[1]), Double.parseDouble(e[2])));
						break;
					case "magicalman":
						toReturn.add(new MagicalEntity(Double.parseDouble(e[1]), Double.parseDouble(e[2])));
						break;	
					case "testboss":
						toReturn.add(new TestBoss(Double.parseDouble(e[1]), Double.parseDouble(e[2])));
						break;
					case "enchantedring":
						toReturn.add(new EnchantedRing(Double.parseDouble(e[1]), Double.parseDouble(e[2]),e[3]));
						break;
					case "villager":
						toReturn.add(new Villager(Double.parseDouble(e[1]), Double.parseDouble(e[2])));
						break;
					case "chest":
						toReturn.add(new Chest(Double.parseDouble(e[1]), Double.parseDouble(e[2]),e[3]));
						break;
					default:
						break;
					case "portal":
						toReturn.add(
								new Portal(
								Double.parseDouble(e[1]),
								Double.parseDouble(e[2]),
								e[3]
								));
						break;
				}
			});
			return toReturn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check collision with another entity
	 * @param str Entity to check collision with
	 * @return True if it collides, false if not
	 */
	public static Entity getByName(String str,double posX,double posY) {
		
		switch (str.toLowerCase()) {
		case "orc":
			return new Orc(posX,posY,0.1,2);
		case "wizard":
			return new Wizard(posX,posY);
		case "sword":
			return new Sword(TileSet.ITEMS_TILES,posX,posY, Game.tileSize, Game.tileSize);
		case "bow":
			return new Bow(TileSet.ITEMS_TILES,posX,posY, Game.tileSize, Game.tileSize);
		case "pillar":
			return new Pillar(TileSet.DANGEON_TILES,posX,posY);
		case "magicalman":
			return new MagicalEntity(posX,posY);
		case "testboss":
			return new TestBoss(posX, posY);
		case "villager":
			return new Villager(posX,posY);
		default:
			break;
		}
		return null;
	}
	
	public static Entity getPickableByName(String str,double posX,double posY,String effect) {
		
		switch (str.toLowerCase()) {
		case "enchantedring":
			return new EnchantedRing(posX, posY, effect);
		case "armor":
			return new Armor(TileSet.ITEMS_TILES,posX,posY,effect);
		default:
			break;
		}
		return null;
	}

	public boolean checkCollision(Entity entity) {
		if (entity.getCollisionShape() == null)
			return false;

		
		boolean collide = getCollisionShape().intersects(entity.getCollisionShape().getLayoutBounds());
		return collide;
	}

	/**
	 * Check collision with a shape
	 * @param shape Shape to check collision with
	 * @return True if it collides, false if not
	 */
	public boolean checkCollision(Shape shape) {
		boolean collide = getCollisionShape().intersects(shape.getLayoutBounds());
		return collide;
	}

	/**
	 * Draws the entity on the world
	 * @param gc GraphicsContext to draw on
	 * @param camera World camera
	 * @param scaleX X scale of the entity
	 * @param scaleY Y scale of the entity
	 */
	protected void drawInternal(GraphicsContext gc, Camera camera, double scaleX,double scaleY) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX(), Game.SCREEN_CENTER_Y - camera.getY() + getPosY(),scaleX,scaleY);
	}

	/**
	 * Draws the entity on the world
	 * @param gc GraphicsContext to draw on
	 * @param camera World camera
	 * @param scale Entity scale
	 */
	protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX() - getWidth() / 2 * scale, Game.SCREEN_CENTER_Y - camera.getY() + getPosY() - getHeight() / 2 * scale, Game.tileSize * scale, Game.tileSize * scale);
	}

	/**
	 * Returns the distance to another entity
	 * @param e Entity to compare with
	 * @return Distance between {@code this} and {@code e}
	 */
	public double getDistanceToEntity(Entity e) {
		return Math.sqrt(Math.pow((e.getPosX() + (e.width/2)) - (this.getPosX() + (this.width/2)), 2) + Math.pow((e.getPosY() + (e.height/2)) - (this.getPosY() + (this.height/1.5)), 2));
	}

	/**
	 * Returns the distance to a tile
	 * @param x Tile position X
	 * @param y Tile position Y
	 * @return Distance between {@code this} and the tile position
	 */
	public double getDistanceToTile(int x, int y) {
		return Math.sqrt(Math.pow((x + (Game.tileSize/2)) - (this.getPosX() + (this.width/2)), 2) + Math.pow((y + (Game.tileSize/2)) - (this.getPosY() + (this.height/1.5)), 2));
	}

	/**
	 * @param e Entity from which to get the vector to
	 * @return Vector to {@code e}
	 */
	public Vector2D getVector2DToEntity(Entity e) {
		Vector2D direction = new Vector2D((e.getPosX() + (e.width/2)) - (this.getPosX() + (this.width/2)), (e.getPosY() + (e.height/2)) - (this.getPosY() + (this.height/1.5)));
		direction = direction.normalize();

		return direction;
	}

	/**
	 * @param x Tile position X
	 * @param y Tile position Y
	 * @return Vector to the tile
	 */
	public Vector2D getVector2DToTile(int x, int y) {
		Vector2D direction = new Vector2D((x + (Game.tileSize/2)) - (this.getPosX() + (this.width/2)), (y + (Game.tileSize/2)) - (this.getPosY() + (this.height/1.5)));
		direction = direction.normalize();

		return direction;
	}

	/**
	 * @param direction Movement vector of an entity
	 * @return Direction of the vector
	 */
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

	/**
	 * @param d Direction of an entity
	 * @return Normalized vector of the direction
	 */
	public static Vector2D getVectorFromDirection(Direction d) {
	    switch (d) {
	        case NORTH:
	            return new Vector2D(0, -1);
	        case EAST:
	            return new Vector2D(-1, 0);
	        case SOUTH:
	            return new Vector2D(0, 1);
	        case WEST:
	            return new Vector2D(1, 0);
	        default:
	            throw new IllegalArgumentException("Invalid direction: " + d);
	    }
	}

	/**
	 * @return Position of the entity
	 */
	public Vector2D getPosition() {
		return new Vector2D(getPosX(),getPosY());
	}

	/**
	 * @return Tile the entity is in
	 */
	public Vector2D getTilePosition() {
		return new Vector2D(getPosX()/Game.tileSize,getPosY()/Game.tileSize);
	}

	/**
	 * Pushes an entity out of another
	 * @param collition Entity to collide with
	 * @param force Force to apply
	 * @return Distance between entities
	 */
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

	/**
	 * Pushes the player out of an entity
	 * @param player Player
	 * @param collition Collision to push out from
	 */
	public static void knockback(Entity player, Entity collition) {
		Task<Void> t = new Knockback(player, collition, 4, 0.2);
		
		new Thread(t).start();
	}

	/**
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return Random value between the specified interval
	 */
	public static float randomFromInterval(float min, float max) {
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
