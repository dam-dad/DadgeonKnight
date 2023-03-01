package nx.engine.world.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Animation;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.util.*;

public class Skeleton extends MobEntity {

	String walkSet = "/assets/textures/skeleton/skeleton_walk.png";

	public double ANIMATION_SPEED = 0.2;
	private double walkAnimationSpeed;
	private double runAnimationSpeed;

	private double time = 0.0;
	private double timeAlerted = 0.0;
	private final double timeToChange = 4.5;
	private final double timeToFollow = 7.5;

	public String state = "walk";
	private double initialSpeed;
	private double runSpeed;
	
	private static final double attackDelay = 0.4;
	private double timeSinceLastAttack = 0.0;

	Player p = Player.get();

	List<Optional<Skeleton>> sklOptional;

	private final Map<Direction, Animation> walk = new HashMap<>() {
		{
			put(Direction.EAST, new Animation(ANIMATION_SPEED, walkSet, 2, 32, 48));
			put(Direction.WEST, new Animation(ANIMATION_SPEED, walkSet, 1, 32, 48));
			put(Direction.NORTH, new Animation(ANIMATION_SPEED, walkSet, 3, 32, 48));
			put(Direction.SOUTH, new Animation(ANIMATION_SPEED, walkSet, 0, 32, 48));
		}
	};

	public Skeleton(double posX, double posY, double speed, double runSpeed) {
		super(posX * Game.tileSize, posY * Game.tileSize);

		this.speed = speed;
		initialSpeed = speed;
		this.runSpeed = runSpeed;
		this.scale = 1.5;

		this.sizeTextureX = 32;
		this.sizeTextureY = 48;

		this.width = this.sizeTextureX * this.scale;
		this.height = this.sizeTextureY * this.scale;

		this.walkAnimationSpeed = ANIMATION_SPEED;
		this.runAnimationSpeed = 0.2;

		this.sizePlayerDetection = 250;
		this.sizeMobDetection = 800;

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

	public void alerted() {
		timeAlerted = 0.0;
		this.state = "alerted";
		this.speed = runSpeed;
		ANIMATION_SPEED = runAnimationSpeed;
	}

	public void followPlayer(double realSpeed) {
		Vector2D direction = getVector2DToEntity(p);
		this.direction = getDirectionFromVector2D(direction);
		animation = walk.get(this.direction);
		direction = direction.scalarMultiply(realSpeed);

		this.setPosX(this.getPosX() + direction.getX());
		this.setPosY(this.getPosY() + direction.getY());
	}

	@Override
	public void update(double deltaTime) {

		sklOptional = getWorld().getEntities().stream().filter(entity -> entity instanceof Skeleton)
				.filter(e -> e.getDistanceToEntity(this) < sizeMobDetection).map(e -> Optional.of((Skeleton) e))
				.toList();

		if (getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth
				&& getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth
				&& getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth
				&& getPosY() - Game.tileSize < Player.get().getCamera().getY() + Game.screenheigth) {

			double lastAnimationSpeed = ANIMATION_SPEED;
			double distancePlayer = getDistanceToEntity(p);
			double realSpeed = this.speed * Game.LastFrameRate * deltaTime;

			if (!state.equals("alerted") && (distancePlayer < this.sizePlayerDetection)) {
				if (!state.equals("follow")) {
					follow();
					if (!sklOptional.isEmpty()) {
						for (Optional<Skeleton> s : sklOptional) {
							if (s.get() != this)
								s.get().alerted();
						}
					}
				}
			} else if (!state.equals("alerted")) {
				if (!state.equals("walk")) {
					walk();
				}
			}

			if (timeSinceLastAttack > attackDelay && this.checkCollision(Game.player)) {
				timeSinceLastAttack = 0;
				Game.inputHandler.ClearActiveKeys();
				Player.get().setVectorMovement(new Vector2D(0,0));
				Player.get().pushOut(this,Player.PLAYER_FORCE * 5);
				Game.player.getAttacked(1);
				walk();
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
				followPlayer(realSpeed);
				break;

			case "alerted":
				followPlayer(realSpeed);
				if (timeAlerted > timeToFollow) {
					walk();

				}
				timeAlerted += deltaTime;
				break;
			default:
				break;
			}

			if (lastAnimationSpeed != ANIMATION_SPEED)
				Animation.updatadeMapDuration(walk, ANIMATION_SPEED);

			animation.update(deltaTime);
		}
	}
}
