package nx.engine.entity;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import nx.engine.Animation;
import nx.engine.Game;
import nx.util.Direction;

public class Orco extends MobEntity<Rectangle> {
	
	String walkTileSet = "/assets/textures/orc/muscleman.png";
	
	public double ANIMATION_SPEED = 0.4;
	
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
	
	public Orco(double posX, double posY,Player player) {
		super(player);
		
		this.posX = posX;
		this.posY = posY;
	
		
		this.scale = 2;
		
		direction = Direction.values()[0];
		
		this.animation = wakl.get(direction);
		
	}
	
	public void update(double deltaTime) {
		
		animation.update(deltaTime);
	}
	
	@Override
	public Rectangle getCollisionShape() {
		return new Rectangle(posX,posY,sizeTextureX * scale,sizeTextureY * scale);
	}
	

}
