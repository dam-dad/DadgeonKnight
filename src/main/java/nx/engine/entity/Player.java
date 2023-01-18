package nx.engine.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.InputHandler;
import nx.engine.tile.TileManager;
import nx.util.Direction;

public class Player extends Entity<Rectangle> {
	
	private int speed;
	
	public double screenX;
	public double screenY;
	
	private static final double ANIMATION_SPEED = 0.15;
	
	private KeyCode[] keyBinds;
	private boolean isWalking = false;
	private Direction direction;
	private Animation animation;
	
	String walkTileSet = "/assets/textures/player/Character_007.png";
	
	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(walkTileSet,0,Game.tileSize,Game.tileSize));
		put(Direction.EAST, new Animation(walkTileSet,1,Game.tileSize,Game.tileSize));
		put(Direction.WEST, new Animation(walkTileSet,2,Game.tileSize,Game.tileSize));
		put(Direction.NORTH, new Animation(walkTileSet,3,Game.tileSize,Game.tileSize));
	}};
	
	private final Map<Direction, Animation> wakl = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0,Game.tileSize,Game.tileSize));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,1,Game.tileSize,Game.tileSize));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,2,Game.tileSize,Game.tileSize));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,3,Game.tileSize,Game.tileSize));
	}};
	
	public Player(double posX, double posY, int speed,InputHandler input) {
		super();
		
		this.posX = posX;
		this.posY = posY;
		
		screenX = Game.screenWidth / 2 - (Game.tileSize/2);
		screenY = Game.screenheigth / 2 - (Game.tileSize/2);
		
		this.width = Game.tileSize;
		this.height = Game.tileSize;
		
		this.speed = speed;
		
		this.direction = Direction.SOUTH;
		
		this.image = new Image("/assets/textures/player/kevin_idle_00.png");
		
		keyBinds = new KeyCode[] {KeyCode.A,KeyCode.D,KeyCode.W,KeyCode.S};
//		keyBinds = new KeyCode[] {KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.DOWN};
		
	}

	@Override
	public void update(Set<KeyCode> activeKeys,double deltaTime) {
		
		   isWalking = false;

		    Vector2D movement = new Vector2D(0.0,0.0);

		    if(activeKeys.contains(keyBinds[0])) {
		        isWalking = true;
		        movement = movement.add(new Vector2D(-1, 0));
		        this.direction = Direction.EAST;
		    }
		    if(activeKeys.contains(keyBinds[1])) {
		        isWalking = true;
		        movement = movement.add(new Vector2D(1, 0));
		        this.direction = Direction.WEST;
		    }
		    if(activeKeys.contains(keyBinds[2])) {
		        isWalking = true;
		        movement = movement.add(new Vector2D(0, -1));
		        this.direction = Direction.NORTH;
		    }
		    if(activeKeys.contains(keyBinds[3])) {
		        isWalking = true;
		        movement = movement.add(new Vector2D(0, 1));
		        this.direction = Direction.SOUTH;
		    }
		    
		    if(movement.getNorm() != 0) {
		    	movement = movement.normalize();
		    }

		    
		    double realSpeed = Math.ceil(this.speed * Game.LastFrameRate * deltaTime);
		    movement = movement.scalarMultiply(realSpeed);

		    movePlayer(movement);

		    if(isWalking) {
		        animation = wakl.get(direction);
		    }else {
		        animation = idle.get(direction);
		    }

		    animation.update(deltaTime);
		
		    
//		    System.out.println(movement + " " + "[" + screenX + " " + screenY + "]" +  "  " + "[" + posX + " " + posY + "]");
	}

	@Override
	public void draw(GraphicsContext gc) {
//		gc.fillRect(screenX, screenY, Game.tileSize, Game.tileSize);
		gc.setFill(Color.WHITE);
		gc.fillRect(screenX + (Game.tileSize/2)/2, screenY + (Game.tileSize/2), (Game.tileSize/2), (Game.tileSize/2));
		
		gc.drawImage(animation.getCurrentFrame(), screenX - ((Game.tileSize/2) * 0.5), screenY - Game.tileSize/2,Game.tileSize * 1.5,Game.tileSize * 1.5);
	}

	@Override
	public Rectangle getCollisionShape() {
//		return new Rectangle(posX,posY,(Game.tileSize),(Game.tileSize));
		return new Rectangle(posX + (Game.tileSize/2)/2,posY + (Game.tileSize/2),(Game.tileSize/2),(Game.tileSize/2));
	}
	@Override
	public double pushOut(Entity collition,double force) {
		double distance = Math.sqrt(Math.pow((collition.posX + (Game.tileSize/2)) - (this.posX + (Game.tileSize/2)), 2) + Math.pow((collition.posY + (Game.tileSize/2)) - (this.posY + ((Game.tileSize/2) * 1.5)), 2));
		
		Vector2D collisionNormal = new Vector2D(
				((collition.posX + (Game.tileSize/2))  - collition.width/2) - ((this.posX + (Game.tileSize/2)) - this.width/2), 
				((collition.posY + (Game.tileSize/2)) - collition.height/2) - ((this.posY + ((Game.tileSize/2) * 1.5)) - this.height/2));
		collisionNormal = collisionNormal.normalize();
		
		Vector2D movement = collisionNormal.scalarMultiply(distance).scalarMultiply(force).scalarMultiply(-1);
			
		movePlayer(movement);
		
		return distance;
	}
	
	public void movePlayer(Vector2D movement) {
	    int halfX = ((Game.screenWidth/2) - (Game.tileSize/2));
	    int halfY = ((Game.screenheigth/2) - (Game.tileSize/2));
	    
	    double movementX = movement.getX();
	    double movementY = movement.getY();

	    if(this.posX + movement.getX() > halfX  && this.posX + movement.getX() < TileManager.worldWidth - halfX - Game.tileSize) {
	        this.posX += movementX;
	        if(this.screenX != 350.0) {
	        	this.screenX = 350.0;
	        }
	    } else {
	    	this.posX += movementX;
	        this.screenX += movementX;
	    }

	    if(this.posY + movement.getY() > halfY && this.posY + movement.getY() < TileManager.worldHeigth - halfY - Game.tileSize) {
	        this.posY += movementY;
	        if(this.screenY != 264.0) {
	        	this.screenY = 264.0;
	        }
	    } else {
	    	this.posY += movementY;
	        this.screenY += movementY;
	    }
	}

}
