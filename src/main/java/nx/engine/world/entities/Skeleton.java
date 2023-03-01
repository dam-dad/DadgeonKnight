package nx.engine.world.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.util.*;

/**
 * Represents a skeleton entity
 */
public class Skeleton extends MobEntity {

	String walkSet = "/assets/textures/skeleton/skeleton-walk.png";
	String idleSet = "/assets/textures/skeleton/skeleton-idle.png";
	
	public static final int tileSizeX = 48;
	public static final int tileSizeY = 64;
	
	public double ANIMATION_SPEED = 0.6;
	
	private double time = 0.0;
	private final double timeToChange = 4.5;
	
	public String TeamTag = "Primeros-esqueletos";
	
	public String state = "walk";
	private double initialSpeed;
	private Player player;
	
	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(idleSet,0,tileSizeX,tileSizeY));
		put(Direction.EAST, new Animation(idleSet,0,tileSizeX,tileSizeY));
		put(Direction.WEST, new Animation(idleSet,0,tileSizeX,tileSizeY));
		put(Direction.NORTH, new Animation(idleSet,0,tileSizeX,tileSizeY));
	}};
	
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkSet,0,tileSizeX,tileSizeY));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkSet,0,tileSizeX,tileSizeY));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkSet,0,tileSizeX,tileSizeY));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkSet,0,tileSizeX,tileSizeY));
	}};

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 * @param speed Entity speed
	 * @param player Player to attack
	 */
	public Skeleton(double posX, double posY, double speed, Player player) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.speed = speed;
		initialSpeed = speed;
		this.scale = 1;
		this.player = player;
		
		this.sizeTextureX = tileSizeX;
		this.sizeTextureY = tileSizeY;
		
		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;
		
		this.direction = Direction.EAST;
		this.animation = walk.get(direction);
		
	}

	/**
	 * Updates the skeleton
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {
		
		
		List<Skeleton> esqueletosFaciles = getWorld().getEntities().stream().filter(entity -> entity instanceof Skeleton)
				.map(entity -> (Skeleton) entity).filter(e -> e.TeamTag.equals("Primeros-esqueletos")).collect(Collectors.toList());
		
		esqueletosFaciles.forEach(e -> e.getAttacked(10));
	}
	
}
