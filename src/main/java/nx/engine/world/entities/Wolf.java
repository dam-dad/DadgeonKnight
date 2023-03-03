package nx.engine.world.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.util.Direction;

/**
 * Represents the wolf entity
 */
public class Wolf extends MobEntity {

	String walkTileSet = "/assets/textures/wolf/wolf.png";

	public static final int tileSizeX = 48;
	public static final int tileSizeY = 64;

	public double ANIMATION_SPEED = 1;

	private double time = 0.0;
	private final double timeToChange = 5.0;

	public String state = "walk";
	private double initialSpeed;
	
	private static final double attackDelay = 0.4;
	private double timeSinceLastAttack = 0.0;

	@SuppressWarnings("serial")
	private final Map<Direction, Animation> walk = new HashMap<>() {
		{
			put(Direction.SOUTH, new Animation(ANIMATION_SPEED, walkTileSet, 2, tileSizeX, tileSizeY));
			put(Direction.EAST, new Animation(ANIMATION_SPEED, walkTileSet, 1, tileSizeX, tileSizeY));
			put(Direction.WEST, new Animation(ANIMATION_SPEED, walkTileSet, 3, tileSizeX, tileSizeY));
			put(Direction.NORTH, new Animation(ANIMATION_SPEED, walkTileSet, 0, tileSizeX, tileSizeY));
		}
	};

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 * @param speed Entity speed
	 * @param player Player to attack
	 */
	public Wolf(double posX, double posY, double speed) {
		super(posX * Game.tileSize, posY * Game.tileSize);

		this.speed = speed;
		initialSpeed = speed;
		this.scale = 2;
		
		this.sizeTextureX = tileSizeX;
		this.sizeTextureY = tileSizeY;

		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;

		direction = Direction.SOUTH;

		this.animation = walk.get(direction);
		this.sizePlayerDetection = 150;

	}

	/**
	 * Changes the direction randomly
	 */
	public void changeDirection() {
		direction = Direction.values()[new Random().nextInt(4)];
		this.animation = walk.get(direction);
	}

	/**
	 * Sets the state to stopped
	 */
	public void stop() {
		this.state = "stop";
		this.speed = 0.0;
	}

	/**
	 * Resets the state to walking
	 */
	public void reset() {
		this.state = "walk";
		this.speed = initialSpeed;
	}

	/**
	 * Updates the entity
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {
		if(getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Player.get().getCamera().getY() + Game.screenheigth){
			
			if (this.mobHealth < 0) {
				getWorld().removeEntity(this);
				return;
			}
			
			double distance = getDistanceToEntity(Player.get());

			double realSpeed = this.speed * Game.LastFrameRate * deltaTime;

			if (distance > this.sizePlayerDetection) {
				if (!state.equals("stop")) {
					state = "stop";
					this.speed = initialSpeed;
				}
			} else {
				if (!state.equals("follow") && timeSinceLastAttack > attackDelay) {
					state = "follow";
					this.speed = initialSpeed * 2;
				}
			}
			
			if (timeSinceLastAttack > attackDelay && this.checkCollision(Game.player)) {
				timeSinceLastAttack = 0;
				Game.inputHandler.ClearActiveKeys();
				Player.get().setVectorMovement(new Vector2D(0,0));
				this.pushOut(Player.get(),Player.PLAYER_FORCE * 10);
				Game.player.getAttacked(2);
				state = "stop";
				return;

			}
			timeSinceLastAttack += deltaTime;

			switch (state) {
			case "stop":
				break;
			case "walk":
				time += deltaTime;

				if (time > timeToChange) {
					changeDirection();
					time = 0;
				}

				if (direction == Direction.EAST) {
					this.setPosX(this.getPosX() + realSpeed);
				} else if (direction == Direction.WEST) {
					this.setPosX(this.getPosX() - realSpeed);
				} else if (direction == Direction.NORTH) {
					this.setPosY(this.getPosY() - realSpeed);
				} else if (direction == Direction.SOUTH) {
					this.setPosY(this.getPosY() + realSpeed);
				}
				break;
			case "follow":
				Vector2D direction = getVector2DToEntity(Player.get());
				this.direction = getDirectionFromVector2D(direction);
				animation = walk.get(this.direction);
				direction = direction.scalarMultiply(realSpeed);

				this.setPosX(this.getPosX() + direction.getX());
				this.setPosY(this.getPosY() + direction.getY());
				break;
			default:
				break;

			}

			animation.update(deltaTime);
		}

	}
}
