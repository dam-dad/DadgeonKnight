package nx.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import nx.engine.tile.TileSetManager;
import nx.util.Direction;

/**
 * Represents an animation for a texture
 */
public class Animation {
	
	private double duration;
	private double timeAcc = 0;
	private List<Image> frames;
	private int counter = 0;
	private boolean loop = true;
	
	private boolean isFinish = false;
	private final double initialDuration;

	/**
	 * Constructor
	 * @param duration Duration of the animation
	 * @param animationSet File to load the animation from
	 * @param line Line to load the textures from
	 * @param tileSizeX Tile width
	 * @param tileSizeY Tile height
	 */
	public Animation(double duration,String animationSet,int line,int tileSizeX,int tileSizeY) {
		this.duration = duration;
		this.initialDuration = duration;
		frames = Arrays.asList(TileSetManager.loadLineOfTiles(new Image(animationSet),line,tileSizeX,tileSizeY));

	}
	/**
	 * Constructor
	 * @param duration Duration of the animation
	 * @param animationSet File to load the animation from
	 * @param line Line to load the textures from
	 * @param tileSizeX Tile width
	 * @param tileSizeY Tile height
	 * @param loop Defines if animation loops
	 */
	public Animation(double duration,String animationSet,int line,int tileSizeX,int tileSizeY,boolean loop) {
		this(duration,animationSet, line, tileSizeX, tileSizeY);
		this.loop = loop;
	}

	/**
	 * Constructor
	 * @param images File to load the textures from
	 * @param line Line to load
	 * @param tileSizeX Tile width
	 * @param tileSizeY Tile height
	 */
	public Animation(String images,int line,int tileSizeX,int tileSizeY) {
		this(-1, images,line,tileSizeX,tileSizeY);
	}

	/**
	 * Updates the animation
	 * @param timeDifference Frame delta time
	 */
	public void update(double timeDifference) {
		if(isFinish)
			return;
		
		if(duration > -1) {
			if (timeAcc > duration) {
				counter++;
				if (counter >= frames.size()) {
					if(!loop) {
						isFinish = true;
						duration = -1;
					}
					counter = 0;
				}
				timeAcc = 0;
			}
			timeAcc += timeDifference;
		}

	}

	/**
	 * Resets the animation
	 * @return {@code this}
	 */
	public Animation reset() {
		this.counter = 0;
		this.duration = initialDuration;
		this.isFinish = false;
		return this;
	}

	/**
	 * Plays the animation
	 * @return {@code this}
	 */
	public Animation play() {
		this.duration = initialDuration;
		return this;
	}

	/**
	 * Stops the animation
	 * @return {@code this}
	 */
	public Animation stop() {
		this.duration = -1;
		return this;
	}

	/**
	 * Returns if animation is paused
	 * @return True if animation is paused, false if not0
	 */
	public boolean isPause() {
		return duration <= -1;
	}

	/**
	 * Returns if animation has stopped
	 * @return True if animation has finished, false if not
	 */
	public boolean isFinish() {
		return this.isFinish;
	}

	/**
	 * Returns the current frame
	 * @return Current frame
	 */
	public Image getCurrentFrame() {
		return frames.get(counter);
	}

	/**
	 * Updates the animations
	 * @param animations Animations to update
	 * @param duration Duration of the animation
	 * @return Map with an animation for each direction
	 */
	public static Map<Direction, Animation> updatadeMapDuration(Map<Direction, Animation> animations,double duration) {
		Map<Direction, Animation> toReturn = animations;
		toReturn.values().forEach(e -> {
			e.duration = duration;
		});
		return toReturn;
	}

}
