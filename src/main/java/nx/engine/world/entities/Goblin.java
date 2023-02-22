package nx.engine.world.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.control.Alert;
import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.particles.Particle;
import nx.engine.scenes.WorldScene;
import nx.engine.world.MobEntity;
import nx.util.Direction;
import nx.util.Vector2f;

public class Goblin extends MobEntity {

private final String walkTileSet = "/assets/textures/goblin/goblin-sprite-sheet.png";
	
	public static final int tileSizeX = 19;
	public static final int tileSizeY = 23;

	public double ANIMATION_SPEED = 0.8;
	private double walkAnimationSpeed;
	private double runAnimationSpeed;
	
	private double time = 0.0;
	private final double timeToChange = 5.0;
	private double timeRunning = 0.0;
	private final double timeToRun = 3.5;
	
	public String state = "walk";
	private double initialSpeed;
	private double runSpeed;
	
	private int selectionInventory = 0;
	
	Optional<Player> playerOptional;
	
	private Entity pickedItem;
	private List<Entity> inventory = new ArrayList<>();
	
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0,tileSizeX,tileSizeY));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,2,tileSizeX,tileSizeY));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,1,tileSizeX,tileSizeY));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,3,tileSizeX,tileSizeY));
	}};
	
	
	public Goblin(double posX, double posY, double speed,double runSpeed) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.speed = speed;
		initialSpeed = speed;
		this.runSpeed = runSpeed;
		this.scale = 2;
		
		this.sizeTextureX = tileSizeX;
		this.sizeTextureY = tileSizeY;
		
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
	public void run() {
		this.state = "run";
		this.speed = runSpeed;
		ANIMATION_SPEED = runAnimationSpeed;
	}
	
	public void scapeFromPlayer(double realSpeed) { 
		Vector2D huida = getVector2DToEntity(playerOptional.get()).negate();
		this.direction = getDirectionFromVector2D(huida);
		animation = walk.get(this.direction);
		huida = huida.scalarMultiply(realSpeed);
		
		this.setPosX(this.getPosX() + huida.getX());
		this.setPosY(this.getPosY() + huida.getY());
	}
	
	public List<Entity> getInventory() {
		return inventory;
	}
	
	public Entity getItemSelected() {
		return getInventory().size() > 0 ? this.getInventory().get(selectionInventory) : new PickableEntity();
	}

	@Override
	public void update(double deltaTime) {
		if(this.mobHealth < 0) {
			getWorld().addEntity(pickedItem);
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
			
			if(!state.equals("run") && distance < this.sizePlayerDetection) {
				if(!state.equals("follow")) {
					follow();
					if(!this.inventory.isEmpty()) {
						createExclamationEffect(posX, posY);  //*** NO VA AQUI ***
						System.out.println("HOLAAAA");
						run();
					}
				}
			} else if(!state.equals("run")) {
				if(!state.equals("walk")) {
					walk();
				}
			}
			
			if(this.checkCollision(playerOptional.get())) { 
				if((inventory.size() == 1) || (playerOptional.get().getInventory().size() <= 1)) {
					Entity.knockback(playerOptional.get(), this,0.07,playerOptional.get().getCamera());
					playerOptional.get().getAttacked(1);
				} else if((playerOptional.get().getInventory().size() > 1) || (this.inventory.size() < 1)) {
					this.getInventory().add(playerOptional.get().getItemSelected());
					playerOptional.get().getInventory().remove(getItemSelected());
					pickedItem = this.getItemSelected();
					run();
				}
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
				case "run":
					scapeFromPlayer(realSpeed);
					if(timeRunning > timeToRun) {
						walk();
					}
					
					timeRunning += deltaTime;
					break;
				default:
					break;

			}
			
			if(lastAnimationSpeed != ANIMATION_SPEED)
				Animation.updatadeMapDuration(walk, ANIMATION_SPEED);
			
			animation.update(deltaTime);
		}
	}
	
	private void createExclamationEffect(double posX, double posY) {
        for (int i = 0; i < 20; i++) {
            float directionX = randomFromInterval(-1.0f, 1.0f);
            float directionY = randomFromInterval(-1.0f, 1.0f);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(0.0f, 1.0f), WorldScene.exclamation, randomFromInterval(1.0f, 200.0f) + 100.0f));
        }
    }
}
