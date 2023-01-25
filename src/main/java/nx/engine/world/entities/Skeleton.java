package nx.engine.world.entities;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.util.*;

public class Skeleton extends MobEntity {

	String walkSet = "/assets/textures/skeleton/skeleton-walk.png";
	String idleSet = "/assets/textures/skeleton/skeleton-idle.png";
	
	public double ANIMATION_SPEED = 0.6;
	
	private double time = 0.0;
	private final double timeToChange = 4.5;
	
	public String state = "walk";
	private double initialSpeed;
	private Player player;
	
	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(idleSet,0,sizeTextureX,sizeTextureY));
		put(Direction.EAST, new Animation(idleSet,0,sizeTextureX,sizeTextureY));
		put(Direction.WEST, new Animation(idleSet,0,sizeTextureX,sizeTextureY));
		put(Direction.NORTH, new Animation(idleSet,0,sizeTextureX,sizeTextureY));
	}};
	
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkSet,0,sizeTextureX,sizeTextureY));
		put(Direction.EAST, new Animation(ANIMATION_SPEED,walkSet,0,sizeTextureX,sizeTextureY));
		put(Direction.WEST, new Animation(ANIMATION_SPEED,walkSet,0,sizeTextureX,sizeTextureY));
		put(Direction.NORTH, new Animation(ANIMATION_SPEED,walkSet,0,sizeTextureX,sizeTextureY));
	}};
	
	public Skeleton(double posX, double posY, double speed, Player player) {
		super(posX, posY);
		
		this.speed = speed;
		initialSpeed = speed;
		this.scale = 1;
		this.player = player;
		
		this.sizeTextureX = 30;
		this.sizeTextureY = 32;
		
		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;
		
		this.direction = Direction.EAST;
		this.animation = walk.get(direction);
		
	}
//	@Override
//	public void draw(GraphicsContext gc, Camera camera) {
//		double screenX = Game.SCREEN_CENTER_X - camera.getX() + posX;
//		double screenY = Game.SCREEN_CENTER_Y - camera.getY() + posY;
//
//		gc.fillRect(screenX, screenY, sizeTextureX * scale, sizeTextureY * scale);
//		gc.setFill(Color.WHEAT);
//		gc.fillOval(screenX + ((sizeTextureX * scale)/2) - (sizePlayerDetection * 2)/2, screenY + ((sizeTextureY * scale)/2) - (sizePlayerDetection * 2)/2 , sizePlayerDetection * 2, sizePlayerDetection * 2);
//		gc.drawImage(animation.getCurrentFrame(), screenX, screenY,sizeTextureX * scale,sizeTextureY * scale);
//		gc.drawImage(animation.getCurrentFrame(), Game.SCREEN_CENTER_X - camera.getX() + posX, Game.SCREEN_CENTER_Y - camera.getY() + posY, sizeTextureX * scale,sizeTextureY * scale);
//	}

}
