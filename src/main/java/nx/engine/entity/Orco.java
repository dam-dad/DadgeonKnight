package nx.engine.entity;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class Orco extends MobEntity<Rectangle> {
	
	String walkTileSet = "/assets/textures/orc/muscleman.png";
	
	private static final double ANIMATION_SPEED = 0.15;
	private static final int sizeTextureX = 32;
	private static final int sizeTextureY = 64;
	
	private Direction direction;
	private Animation animation;
	
	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(walkTileSet,0,sizeTextureX,sizeTextureY));
		put(Direction.EAST, new Animation(walkTileSet,1,sizeTextureX,sizeTextureY));
		put(Direction.WEST, new Animation(walkTileSet,2,sizeTextureX,sizeTextureY));
		put(Direction.NORTH, new Animation(walkTileSet,3,sizeTextureX,sizeTextureY));
	}};
	
	private final Map<Direction, Animation> wakl = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0,sizeTextureX,sizeTextureY));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkTileSet,1,sizeTextureX,sizeTextureY));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkTileSet,2,sizeTextureX,sizeTextureY));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkTileSet,3,sizeTextureX,sizeTextureY));
	}};
	
	public Orco(double posX, double posY) {
		super();
		
		this.posX = posX;
		this.posY = posY;
		
		direction = Direction.SOUTH;
		
		animation = wakl.get(direction);
		
	}
	
	public void update(double deltaTime) {
		animation.update(deltaTime);
	}
	@Override
	public void draw(GraphicsContext gc) {
		
		gc.drawImage(animation.getCurrentFrame(),posX,posY,sizeTextureX,sizeTextureY);
	}
	

}
