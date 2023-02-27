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

public abstract class Entity {
	
	protected double posX;
	protected double posY;
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
	public Entity(Image image,double posX, double posY,double width,double height) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.image = image;
		this.width = width;
		this.height = height;
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
	protected void setPosition(double x, double y) {
		this.setPosX(x);
		this.setPosY(y);
	}

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
						toReturn.add(new Armor(TileSet.ITEMS_TILES, Double.parseDouble(e[1]), Double.parseDouble(e[2])));
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
						toReturn.add(new EnchantedRing(Double.parseDouble(e[1]), Double.parseDouble(e[2])));
						break;
					case "villager":
						toReturn.add(new Villager(Double.parseDouble(e[1]), Double.parseDouble(e[2])));
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
	
	public boolean checkCollision(Entity entity) {
		if (entity.getCollisionShape() == null)
			return false;

		
		boolean collide = getCollisionShape().intersects(entity.getCollisionShape().getLayoutBounds());
		return collide;
	}
	public boolean checkCollision(Shape shape) {
		boolean collide = getCollisionShape().intersects(shape.getLayoutBounds());
		return collide;
	}

	protected void drawInternal(GraphicsContext gc, Camera camera, double scaleX,double scaleY) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX(), Game.SCREEN_CENTER_Y - camera.getY() + getPosY(),scaleX,scaleY);
	}
	protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX(), Game.SCREEN_CENTER_Y - camera.getY() + getPosY(), Game.tileSize * scale, Game.tileSize * scale);
	}

	public double getDistanceToEntity(Entity e) {
		return Math.sqrt(Math.pow((e.getPosX() + (e.width/2)) - (this.getPosX() + (this.width/2)), 2) + Math.pow((e.getPosY() + (e.height/2)) - (this.getPosY() + (this.height/1.5)), 2));
	}
	public double getDistanceToTile(Vector2D e) {
		return Math.sqrt(Math.pow((e.getX() + (Game.tileSize/2)) - (this.getPosX() + (this.width/2)), 2) + Math.pow((e.getY() + (Game.tileSize/2)) - (this.getPosY() + (this.height/2)), 2));
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
	
	public Vector2D getPosition() {
		return new Vector2D(getPosX(),getPosY());
	}
	public Vector2D getTilePosition() {
		return new Vector2D(getPosX()/Game.tileSize,getPosY()/Game.tileSize);
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
	public static void knockback(Entity player,Entity collition) {
		
		Task<Void> t = new Knockback(player, collition, 4, 0.2);
		
		new Thread(t).start();
	}
	public static float randomFromInterval(float min, float max) { // min and max included 
  	  return (float) (Math.random() * (max - min + 1) + min);
	}
	
	
	//Getters / Setters

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
