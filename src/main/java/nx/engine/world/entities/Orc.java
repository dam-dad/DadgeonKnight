package nx.engine.world.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import nx.engine.world.MobEntity;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.tile.PathfindingManager;
import nx.util.Direction;

/**
 * Represents an orc entity
 */
public class Orc extends MobEntity {

	private final String walkTileSet = "/assets/textures/orc/muscleman.png";

	public static final int tileSizeX = 32;
	public static final int tileSizeY = 64;

	public double ANIMATION_SPEED = 0.8;
	private double walkAnimationSpeed;
	private double runAnimationSpeed;

	private double time = 0.0;
	private final double timeToChange = 5.0;

	public String state = "walk";
	private double walkSpeed;
	private double runSpeed;
	
	private static final double attackDelay = 0.1;
	private double timeSinceLastAttack = 0.0;

	private List<? extends Entity> dropItems;

	private static List<Vector2D> movementToPlayer = new ArrayList<Vector2D>();
	private Vector2D nextPosition;
	private boolean taskExecuting = false;

	private final Map<Direction, Animation> walk = new HashMap<>() {
		{
			put(Direction.SOUTH, new Animation(ANIMATION_SPEED, walkTileSet, 0, tileSizeX, tileSizeY));
			put(Direction.EAST, new Animation(ANIMATION_SPEED, walkTileSet, 2, tileSizeX, tileSizeY));
			put(Direction.WEST, new Animation(ANIMATION_SPEED, walkTileSet, 1, tileSizeX, tileSizeY));
			put(Direction.NORTH, new Animation(ANIMATION_SPEED, walkTileSet, 3, tileSizeX, tileSizeY));
		}
	};

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 * @param speed Speed
	 * @param runSpeed Run speed
	 */
	public Orc(double posX, double posY, double speed, double runSpeed) {
		super(posX * Game.tileSize, posY * Game.tileSize);

		this.walkSpeed = speed;
		this.runSpeed = runSpeed;
		this.scale = 2;
		
		this.speed = walkSpeed;

		this.sizeTextureX = tileSizeX;
		this.sizeTextureY = tileSizeY;

		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;

		this.walkAnimationSpeed = ANIMATION_SPEED;
		this.runAnimationSpeed = 0.2;

		this.sizePlayerDetection = 250;
		
		this.timeSinceLastHit = MobEntity.TIME_SHOWING_ATTACK;

		direction = Direction.values()[2];

		this.animation = walk.get(direction);
		
		follow();
	}

	/**
	 * Set a new random direction
	 */
	public void changeDirection() {
		direction = Direction.values()[new Random().nextInt(4)];
		this.animation = walk.get(direction);
	}

	public void stop() {
		this.state = "stop";
		this.speed = 0.0;
		ANIMATION_SPEED = 0;
	}

	public void walk() {
		this.state = "walk";
		this.speed = walkSpeed;
		ANIMATION_SPEED = walkAnimationSpeed;
	}

	public void follow() {
		this.state = "follow";
		this.speed = runSpeed;
		ANIMATION_SPEED = runAnimationSpeed;
	}
	
	@Override
	public Vector2D getPosition() {
		return new Vector2D(getPosX(),getPosY() + (sizeTextureY * scale) - Game.tileSize);
	}

	@Override
	public Vector2D getTilePosition() {
		return new Vector2D(getPosX()/Game.tileSize,(getPosY() + (sizeTextureY * scale) - Game.tileSize)/Game.tileSize);
	}

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
			double lastAnimationSpeed = ANIMATION_SPEED;

			double distance = getDistanceToEntity(Player.get());
			double realSpeed = this.speed * Game.LastFrameRate * deltaTime;

			if (timeSinceLastAttack > attackDelay && this.checkCollision(Game.player)) {
				timeSinceLastAttack -= attackDelay;
				Game.inputHandler.ClearActiveKeys();
				Player.get().setVectorMovement(new Vector2D(0,0));
				Entity.knockback(Game.player, this);
				Game.player.getAttacked(1);
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
				if(Player.get().isWalking()) {
					PathfindingManager p = new PathfindingManager(getTilePosition());
					
					p.setOnSucceeded(event -> {
					    movementToPlayer = p.getValue();
					    if(movementToPlayer != null)
					    	nextPosition = this.getPosition().add(movementToPlayer.get(movementToPlayer.size() -1).scalarMultiply(-1).scalarMultiply(48));
					    taskExecuting = false;
					});
					p.setOnCancelled(event -> {
						movementToPlayer = null;
						nextPosition = null;
						taskExecuting = false;
					});
					
					if(p.isRunning())
						p.cancel();
					new Thread(p).start();
					taskExecuting = true;
				}

				
				if(!taskExecuting && movementToPlayer != null && movementToPlayer.size() > 0) {
					Vector2D direction = getVector2DToEntity(Player.get());
					this.direction = getDirectionFromVector2D(direction);
					animation = walk.get(this.direction);
					move(movementToPlayer.get(movementToPlayer.size() -1).scalarMultiply(-1).scalarMultiply(realSpeed));
					if(getPosition().distanceSq(nextPosition) < Game.tileSize) {
						movementToPlayer.remove(movementToPlayer.size() -1);
						if(movementToPlayer.size() > 0) {
							Game.logger.log(Level.INFO,movementToPlayer.toString());
							nextPosition = getPosition().add(movementToPlayer.get(movementToPlayer.size() -1).scalarMultiply(-1).scalarMultiply(Game.tileSize));
						}
					}
				}

				break;
			default:
				break;

			}

			if (lastAnimationSpeed != ANIMATION_SPEED)
				Animation.updatadeMapDuration(walk, ANIMATION_SPEED);

			animation.update(deltaTime);
			if(timeSinceLastHit < MobEntity.TIME_SHOWING_ATTACK)
				timeSinceLastHit += deltaTime;
		}
	}

}
