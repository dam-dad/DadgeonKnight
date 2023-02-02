package nx.engine.world.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class Player extends Entity {

	private static final int MAX_PLAYER_HEALTH = 10;
	private static final double TIME_SHOWING_ATTACK = 0.5;
	public static final double PLAYER_FORCE = 0.1;

	public static final String walkTileSet = "/assets/textures/player/CharacterMovementSet.png";
	public static final String swordSet = "/assets/textures/player/player_Sword.png";

	private static final double ANIMATION_SPEED = 0.15;

	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(walkTileSet,0, 22, 25));
		put(Direction.EAST, new Animation(walkTileSet,3, 22, 25));
		put(Direction.WEST, new Animation(walkTileSet,1, 22, 25));
		put(Direction.NORTH, new Animation(walkTileSet,2, 22, 25));
	}};

	private final Map<Direction, Animation> wakl = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0, 22, 25));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,3, 22, 25));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,1, 22, 25));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,2, 22, 25));
	}};
	
	private final Map<Direction, Animation> sword = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,swordSet,0, 22, 25,false).stop());
		put(Direction.EAST, new Animation(ANIMATION_SPEED,swordSet,3, 22, 25,false).stop());
		put(Direction.WEST, new Animation(ANIMATION_SPEED,swordSet,2, 22, 25,false).stop());
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,swordSet,1, 22, 25,false).stop());
	}};
	
	private KeyCode[] wasdKeys = new KeyCode[] {KeyCode.A,KeyCode.D,KeyCode.W,KeyCode.S};
	private KeyCode[] arrowsKeys = new KeyCode[] {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
	
	private int selectionInventory = 0;
	private List<Entity> inventory = new ArrayList<>();

	private final int speed;
	
	public int screenX;
	public int screenY;

	private boolean isWalking = false;
	private boolean isAttacking = false;
	private Direction direction;
	private Animation animation;
	private final Camera camera;

	private int health;
	private double timeSinceLastHit;
	
	public Player(double posX, double posY, int speed, Camera camera) {
		this.setPosX(posX);
		this.setPosY(posY);
		
		screenX = Game.screenWidth / 2 - (Game.tileSize/2);
		screenY = Game.screenheigth / 2 - (Game.tileSize/2);
		
		this.width = Game.tileSize;
		this.height = Game.tileSize;
		
		this.speed = speed;
		this.health = MAX_PLAYER_HEALTH;
		this.timeSinceLastHit = TIME_SHOWING_ATTACK;
		
		this.direction = Direction.SOUTH;
		this.camera = camera;

	}

	@Override
	public void update(double deltaTime) {
		
		Set<KeyCode> activeKeys = Game.inputHandler.getActiveKeys();
		Set<MouseButton> activeButtons = Game.inputHandler.getActiveButtons();

		isWalking = false;
		Vector2D movement = new Vector2D(0.0, 0.0);
		
		if (activeKeys.contains(wasdKeys[0]) || activeKeys.contains(arrowsKeys[0])) {
			isWalking = true;
			movement = movement.add(new Vector2D(-1, 0));
			this.direction = Direction.EAST;
			setAttacking(false);
		}
		else if (activeKeys.contains(wasdKeys[1]) || activeKeys.contains(arrowsKeys[1])) {
			isWalking = true;
			movement = movement.add(new Vector2D(1, 0));
			this.direction = Direction.WEST;
			setAttacking(false);
		}
		if (activeKeys.contains(wasdKeys[2]) || activeKeys.contains(arrowsKeys[2])) {
			isWalking = true;
			movement = movement.add(new Vector2D(0, -1));
			this.direction = Direction.NORTH;
			setAttacking(false);
		}
		else if (activeKeys.contains(wasdKeys[3]) || activeKeys.contains(arrowsKeys[3])) {
			isWalking = true;
			movement = movement.add(new Vector2D(0, 1));
			this.direction = Direction.SOUTH;
			setAttacking(false);
		}
		
		if (activeKeys.contains(KeyCode.E)){
			nextItem();
		}
		else if (activeKeys.contains(KeyCode.Q)){
			previousItem();
		}
		
		if(activeButtons.contains(MouseButton.PRIMARY)) {
			Game.inputHandler.ClearActiveButtons();
			
			PickableEntity p = (PickableEntity) getItemSelected();
			p.useItem();
		}

		if (movement.getNorm() != 0) {
			movement = movement.normalize();
		}

		double realSpeed = Math.ceil(this.speed * Game.LastFrameRate * deltaTime);
		movement = movement.scalarMultiply(realSpeed);

		double movementX = Math.round(movement.getX());
		double movementY = Math.round(movement.getY());

		move(movementX, movementY);

		camera.setPosition(getPosX(), getPosY());
		
		animation = idle.get(direction);
		
		if (isWalking) {
			animation = wakl.get(direction);
		}
		if(isAttacking) {
			animation = sword.get(direction);
			if(animation.isPause() && !animation.isFinish()) {
				sword.get(direction).play();
			}
			else if(animation.isFinish()) {
				sword.get(direction).stop().reset();
				setAttacking(false);
				
			}
		}
		animation.update(deltaTime);
		if(timeSinceLastHit < Player.TIME_SHOWING_ATTACK)
			timeSinceLastHit += deltaTime;
	}

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		//debug collision
//		gc.setFill(Color.BLACK);
//		gc.fillRect(screenX, screenY, Game.tileSize, Game.tileSize);
//		gc.setFill(Color.WHITE);
//		gc.fillRect(screenX + (Game.tileSize/2)/2, screenY + (Game.tileSize/2), (Game.tileSize/2), (Game.tileSize/2));

		gc.drawImage(animation.getCurrentFrame(), screenX - 4, screenY - 10,22 * 2.5,25 * 2.5);

		if (timeSinceLastHit < TIME_SHOWING_ATTACK) {
			double alpha = (1.0 - timeSinceLastHit / TIME_SHOWING_ATTACK) * 0.9;
			gc.setFill(Color.rgb(255, 0, 0, alpha));
			gc.fillOval(screenX - ((Game.tileSize/2) * 0.5), screenY - Game.tileSize/3,Game.tileSize * 1.5,Game.tileSize * 1.5);
		}
	}

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
		return new Rectangle(getPosX() +  ((Game.tileSize/2) * 0.5), getPosY() + (Game.tileSize/2), Game.tileSize/2, Game.tileSize/2);
	}
	
	public Camera getCamera() {
		return camera;
	}
	public List<Entity> getInventory() {
		return inventory;
	}
	public void setAttacking(boolean a) {
		this.isAttacking = a;
	}
	public void AddEntityToInventory(PickableEntity e) {
		getInventory().add(e);
//		getInventory().stream().sorted(Comparator.comparingInt(e::compareTo));
	}
	public Direction getDirection() {
		return direction;
	}
	public void setAnimation(Animation a) {
		this.animation = a;
	}
	public Entity getItemSelected() {
		return getInventory().size() > 0 ? this.getInventory().get(selectionInventory) : new PickableEntity();
	}
	public void nextItem() {
		if((selectionInventory + 1) >= getInventory().size())
			return;
		selectionInventory++;
	}
	public void previousItem() {
		if(selectionInventory <=  0)
			return;
		selectionInventory--;
	}
}
