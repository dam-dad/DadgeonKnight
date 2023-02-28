package nx.engine.world.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.util.Direction;

public class Goblin extends MobEntity {

private final String walkTileSet = "/assets/textures/goblin/goblinsheet.png";
private final String takeTileSet = "/assets/textures/goblin/goblin-take.png";
	
	public double ANIMATION_SPEED = 0.8;
	private double walkAnimationSpeed;
	private double runAnimationSpeed;
	
	private double time = 0.0;
	private final double timeToChange = 5.0;
	
	public String state = "walk";
	private double initialSpeed;
	private double runSpeed;
	
	Optional<Player> playerOptional;
	
	private List<Entity> inventory = new ArrayList<>();
	private List<? extends Entity> dropItems;
	
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0,61,62));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,2,61,62));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,1,61,62));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,3,61,62));
	}};
	
	
	public Goblin(double posX, double posY, double speed,double runSpeed) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.speed = speed;
		initialSpeed = speed;
		this.runSpeed = runSpeed;
		this.scale = 2;
		
		this.sizeTextureX = 32;
		this.sizeTextureY = 64;
		
		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;
		
		this.walkAnimationSpeed = ANIMATION_SPEED;
		this.runAnimationSpeed = 0.2;
		
		this.sizePlayerDetection = 250;
		
		direction = Direction.values()[2];
		
		this.animation = walk.get(direction);
		
		
	}

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
		this.speed = initialSpeed;
		ANIMATION_SPEED = walkAnimationSpeed;
	}
	public void follow() {
		this.state = "follow";
		this.speed = runSpeed;
		ANIMATION_SPEED = runAnimationSpeed;
	}

	@Override
	public void update(double deltaTime) {
		if(this.mobHealth < 0) {
			getWorld().removeEntity(this);
			return;
		}
		
		playerOptional = getWorld().getEntities().stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .findAny();
		
		if(playerOptional.isPresent()) {
			double lastAnimationSpeed = ANIMATION_SPEED;
			
	        double distance = getDistanceToEntity(playerOptional.get());
			double realSpeed = this.speed * Game.LastFrameRate * deltaTime;
			
			if(distance < this.sizePlayerDetection) {
				if(!state.equals("follow")) {
					follow();
				}
			}else {
				if(!state.equals("walk")) {
					walk();
				}
			}
			
			if(this.checkCollision(playerOptional.get())) { //TODO aqui es donde roba el objeto
				
			}

			switch (state) {
				case "stop":
					break;
				case "walk":
					time += deltaTime;

					if(time > timeToChange) {
						changeDirection();
						time = 0;
					}

					if(direction == Direction.EAST) {
						this.setPosX(this.getPosX() + realSpeed);
					}
					else if(direction == Direction.WEST) {
						this.setPosX(this.getPosX() - realSpeed);
					}
					else if(direction == Direction.NORTH) {
						this.setPosY(this.getPosY() - realSpeed);
					}
					else if(direction == Direction.SOUTH) {
						this.setPosY(this.getPosY() + realSpeed);
					}
					break;
				case "follow":
					Vector2D direction = getVector2DToEntity(playerOptional.get());
					this.direction = getDirectionFromVector2D(direction);
					animation = walk.get(this.direction);
					direction = direction.scalarMultiply(realSpeed);

					this.setPosX(this.getPosX() + direction.getX());
					this.setPosY(this.getPosY() + direction.getY());
					break;
				default:
					break;

			}
			
			if(lastAnimationSpeed != ANIMATION_SPEED)
				Animation.updatadeMapDuration(walk, ANIMATION_SPEED);
			
			animation.update(deltaTime);
		}
	}
}