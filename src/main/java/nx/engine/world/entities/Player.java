package nx.engine.world.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class Player extends Entity {

	private static final int MAX_PLAYER_HEALTH = 10;
	private static final double TIME_SHOWING_ATTACK = 0.5;
	public static final double PLAYER_FORCE = 0.1;

	private static final String walkTileSet = "/assets/textures/player/Character_007.png";

	private static final double ANIMATION_SPEED = 0.15;

	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(walkTileSet,0, Game.tileSize, Game.tileSize));
		put(Direction.EAST, new Animation(walkTileSet,1, Game.tileSize, Game.tileSize));
		put(Direction.WEST, new Animation(walkTileSet,2, Game.tileSize, Game.tileSize));
		put(Direction.NORTH, new Animation(walkTileSet,3, Game.tileSize, Game.tileSize));
	}};

	private final Map<Direction, Animation> wakl = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0, Game.tileSize, Game.tileSize));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,1, Game.tileSize, Game.tileSize));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,2, Game.tileSize, Game.tileSize));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,3, Game.tileSize, Game.tileSize));
	}};
	
	private final int speed;
	
	public int screenX;
	public int screenY;
	
	private KeyCode[] keyBinds;
	private boolean isWalking = false;
	private Direction direction;
	private Animation animation;
	private final Camera camera;

	private int health;
	private double timeSinceLastHit;
	
	public Player(double posX, double posY, int speed, Camera camera) {
		this.posX = posX;
		this.posY = posY;
		
		screenX = Game.screenWidth / 2 - (Game.tileSize/2);
		screenY = Game.screenheigth / 2 - (Game.tileSize/2);
		
		this.width = Game.tileSize;
		this.height = Game.tileSize;
		
		this.speed = speed;
		this.health = MAX_PLAYER_HEALTH;
		this.timeSinceLastHit = TIME_SHOWING_ATTACK;
		
		this.direction = Direction.SOUTH;
		this.camera = camera;

		this.image = new Image("/assets/textures/player/kevin_idle_00.png");
		
		keyBinds = new KeyCode[] {KeyCode.A,KeyCode.D,KeyCode.W,KeyCode.S};
//		keyBinds = new KeyCode[] {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};

	}

	@Override
	public void update(double deltaTime) {
		Set<KeyCode> activeKeys = Game.inputHandler.getActiveKeys();

		isWalking = false;
		Vector2D movement = new Vector2D(0.0, 0.0);

		// Update animation
		if (activeKeys.contains(keyBinds[0])) {
			isWalking = true;
			movement = movement.add(new Vector2D(-1, 0));
			this.direction = Direction.EAST;
		}
		if (activeKeys.contains(keyBinds[1])) {
			isWalking = true;
			movement = movement.add(new Vector2D(1, 0));
			this.direction = Direction.WEST;
		}
		if (activeKeys.contains(keyBinds[2])) {
			isWalking = true;
			movement = movement.add(new Vector2D(0, -1));
			this.direction = Direction.NORTH;
		}
		if (activeKeys.contains(keyBinds[3])) {
			isWalking = true;
			movement = movement.add(new Vector2D(0, 1));
			this.direction = Direction.SOUTH;
		}

		if (movement.getNorm() != 0) {
			movement = movement.normalize();
		}

		double realSpeed = Math.ceil(this.speed * Game.LastFrameRate * deltaTime);
		movement = movement.scalarMultiply(realSpeed);

		double movementX = Math.round(movement.getX());
		double movementY = Math.round(movement.getY());

		move(movementX, movementY);

//		this.posX += movementX;
//		this.posY += movementY;

		camera.setPosition(posX, posY);

		if (isWalking) {
			animation = wakl.get(direction);
		} else {
			animation = idle.get(direction);
		}

		animation.update(deltaTime);

		timeSinceLastHit += deltaTime;
	}

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
//		gc.fillRect(screenX, screenY, Game.tileSize, Game.tileSize);
//		gc.setFill(Color.WHITE);
//		gc.fillRect(screenX + (Game.tileSize/2)/2, screenY + (Game.tileSize/2), (Game.tileSize/2), (Game.tileSize/2));

		gc.drawImage(animation.getCurrentFrame(), screenX - ((Game.tileSize/2) * 0.5), screenY - Game.tileSize/2,Game.tileSize * 1.5,Game.tileSize * 1.5);

		if (timeSinceLastHit < TIME_SHOWING_ATTACK) {
			double alpha = (1.0 - timeSinceLastHit / TIME_SHOWING_ATTACK) * 0.9;
			gc.setFill(Color.rgb(255, 0, 0, alpha));
			gc.fillOval(screenX - ((Game.tileSize/2) * 0.5), screenY - Game.tileSize/2,Game.tileSize * 1.5,Game.tileSize * 1.5);
		}
	}
	
//	public double pushOut(Entity collition,double force) {
//		double distance = Math.sqrt(Math.pow((collition.posX + (Game.tileSize/2)) - (this.posX + (Game.tileSize/2)), 2) + Math.pow((collition.posY + (Game.tileSize/2)) - (this.posY + ((Game.tileSize/2) * 1.5)), 2));
//
//		Vector2D collisionNormal = new Vector2D(
//				((collition.posX + (Game.tileSize/2))  - collition.width/2) - ((this.posX + (Game.tileSize/2)) - this.width/2),
//				((collition.posY + (Game.tileSize/2)) - collition.height/2) - ((this.posY + ((Game.tileSize/2) * 1.5)) - this.height/2));
//		collisionNormal.normalize();
//
//		Vector2D movement = collisionNormal.scalarMultiply(distance);
//
//		this.posX -= Math.floor(movement.getX() * force);
//		this.posY -= Math.floor(movement.getY() * force);
//		if(this.screenX < ((Game.screenWidth/2) - Game.tileSize) ||  this.screenX > ((Game.screenWidth/2) + Game.tileSize)) {
//			this.screenX -= Math.floor(movement.getX() * force);
//		}
//		if(this.screenY < ((Game.screenheigth/2) - Game.tileSize) ||  this.screenY > ((Game.screenheigth/2) + Game.tileSize)) {
//			this.screenY -= Math.floor(movement.getY() * force);
//		}
//		return distance;
//	}

	// TODO
	public void getAttacked(int damage) {
		health -= damage;
		timeSinceLastHit = 0;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public Shape getCollisionShape() {
		return new Rectangle(posX, posY, Game.tileSize, Game.tileSize);
	}
}
