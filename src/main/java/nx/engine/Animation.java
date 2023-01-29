package nx.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import nx.engine.tile.TileSetManager;
import nx.util.Direction;

public class Animation {
	
	private double duration;
	private double timeAcc = 0;
	private List<Image> frames;
	private int counter = 0;
	private boolean loop = true;
	
	private boolean isFinish = false;
	private final double initialDuration;
	
	public Animation(double duration,String animationSet,int line,int tileSizeX,int tileSizeY) {
		this.duration = duration;
		this.initialDuration = duration;
		frames = Arrays.asList(TileSetManager.loadLineOfTiles(new Image(animationSet),line,tileSizeX,tileSizeY));

	}
	public Animation(double duration,String animationSet,int line,int tileSizeX,int tileSizeY,boolean loop) {
		this(duration,animationSet, line, tileSizeX, tileSizeY);
		this.loop = loop;
	}
	public Animation(String images,int line,int tileSizeX,int tileSizeY,boolean a) {
		this(-1, images,line,tileSizeX,tileSizeY,a);
	}
	
	public Animation(String images,int line,int tileSizeX,int tileSizeY) {
		this(-1, images,line,tileSizeX,tileSizeY);
	}
	

	

	
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
	public Animation reset() {
		this.counter = 0;
		this.duration = initialDuration;
		this.isFinish = false;
		return this;
	}
	
	public Animation play() {
		this.duration = initialDuration;
		return this;
	}
	public Animation stop() {
		this.duration = -1;
		return this;
	}
	
	public boolean isPause() {
		return duration <= -1;
	}
	public boolean isFinish() {
		return this.isFinish;
	}
	
	public Animation setDuration(double duration) {
		this.duration = duration;
		if(duration > -1) {
			isFinish = false;
		}
		return this;
	}
	
	public List<Image> getFrames() {
		return frames;
	}
	
	public Image getCurrentFrame() {
		return frames.get(counter);
	}
	

	
	public static Map<Direction, Animation> updatadeMapDuration(Map<Direction, Animation> animations,double duration) {
		Map<Direction, Animation> toReturn = animations;
		toReturn.values().forEach(e -> {
			e.duration = duration;
		});
		return toReturn;
	}

}
