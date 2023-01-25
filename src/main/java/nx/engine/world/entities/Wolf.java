package nx.engine.world.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.util.Direction;

public class Wolf extends MobEntity {

	String walkTileSet = "/assets/textures/wolf/wolf.png";

	public double ANIMATION_SPEED = 1.5;

	private double time = 0.0;
	private final double timeToChange = 5.0;

	public String state = "walk";
	private double initialSpeed;
	private Player player;

	@SuppressWarnings("serial")
	private final Map<Direction, Animation> walk = new HashMap<>() {
		{
			put(Direction.SOUTH, new Animation(ANIMATION_SPEED, walkTileSet, 2, 48, 64));
			put(Direction.EAST, new Animation(ANIMATION_SPEED, walkTileSet, 1, 48, 64));
			put(Direction.WEST, new Animation(ANIMATION_SPEED, walkTileSet, 3, 48, 64));
			put(Direction.NORTH, new Animation(ANIMATION_SPEED, walkTileSet, 0, 48, 64));
		}
	};

	public Wolf(double posX, double posY, double speed, Player player) {
		super(posX, posY);

		this.speed = speed;
		initialSpeed = speed;
		this.scale = 2;
		this.player = player;

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
	public void update(double deltaTime) {
		double distance = getDistanceToEntity(player);

		double realSpeed = this.speed * Game.LastFrameRate * deltaTime;

		if (!state.equals("stop")) {
			if (distance > this.sizePlayerDetection) {
				if (!state.equals("walk")) {
					state = "walk";
					this.speed = initialSpeed;
				}
			} else {
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

			if (time > timeToChange) {
				changeDirection();
				time = 0;
			}

			if (direction == Direction.EAST) {
				this.posX += realSpeed;
			} else if (direction == Direction.WEST) {
				this.posX -= realSpeed;
			} else if (direction == Direction.NORTH) {
				this.posY -= realSpeed;
			} else if (direction == Direction.SOUTH) {
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

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		gc.drawImage(animation.getCurrentFrame(), Game.SCREEN_CENTER_X - camera.getX() + posX,
				Game.SCREEN_CENTER_Y - camera.getY() + posY, sizeTextureX * scale, sizeTextureY * scale);
	}

}
