package nx.engine.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class Orco extends MobEntity<Rectangle> {
	
	String walkTileSet = "/assets/textures/orc/muscleman.png";
	
	public double ANIMATION_SPEED = 0.4;
	
	private double time = 0.0;
	private final double timeToChange = 5.0;
	
	public String state = "walk";
	private double initialSpeed;
	
	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(walkTileSet,0,sizeTextureX,sizeTextureY));
		put(Direction.EAST, new Animation(walkTileSet,2,sizeTextureX,sizeTextureY));
		put(Direction.WEST, new Animation(walkTileSet,1,sizeTextureX,sizeTextureY));
		put(Direction.NORTH, new Animation(walkTileSet,3,sizeTextureX,sizeTextureY));
	}};
	
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0,sizeTextureX,sizeTextureY));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,2,sizeTextureX,sizeTextureY));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,1,sizeTextureX,sizeTextureY));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,3,sizeTextureX,sizeTextureY));
	}};
	
	public Orco(double posX, double posY,double speed,Player player) {
		super(player);
		
		this.posX = posX;
		this.posY = posY;
		
		this.speed = speed;
		initialSpeed = speed;
		this.scale = 2;
		
		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;
		
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
	}
	public void reset() {
		this.state = "walk";
		this.speed = initialSpeed;
	}
	
	@Override
	public void update(Set<KeyCode> activeKeys,double deltaTime) {
		if(posX + Game.tileSize > player.posX - Game.screenWidth && 
				posX - Game.tileSize < player.posX + Game.screenWidth &&
				posY + Game.tileSize > player.posY - Game.screenheigth && 
				posY - Game.tileSize  < player.posY + Game.screenheigth)
		{
			double distance = getDistanceToEntity(player);
			
			double realSpeed = this.speed * Game.LastFrameRate * deltaTime;
			
			if(!state.equals("stop")) {
				if(distance > this.sizePlayerDetection) {
					if(!state.equals("walk")) {
						state = "walk";
						this.speed = initialSpeed;
					}
				}else {
					if (!state.equals("follow")) {
						state = "follow";
						this.speed = initialSpeed * 2;
					}
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
					this.posX += realSpeed;
				}
				else if(direction == Direction.WEST) {
					this.posX -= realSpeed;
				}
				else if(direction == Direction.NORTH) {
					this.posY -= realSpeed;
				}
				else if(direction == Direction.SOUTH) {
					this.posY += realSpeed;
				}
				break;
			case "follow":
				Vector2D direction = getVector2DToEntity(player);
				this.direction = getDirectionFromVector2D(direction);
				animation = walk.get(this.direction);
				direction = direction.scalarMultiply(realSpeed);
				
				this.posX += direction.getX();
				this.posY += direction.getY();
				break;
			default:
				break;
			
			}
			
			animation.update(deltaTime);
		}
		
	}

	
	@Override
	public Rectangle getCollisionShape() {
		return new Rectangle(posX,posY,sizeTextureX * scale,sizeTextureY * scale);
	}
	

}
