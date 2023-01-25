package nx.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import nx.engine.tile.TileSet;
import nx.util.Direction;

public class Animation {
	
	private double duration;
	private double timeAcc = 0;
	private List<Image> frames;
	private int counter = 0;
	
	public Animation(double duration,String animationSet,int line,int tileSizeX,int tileSizeY) {
		this.duration = duration;
		frames = Arrays.asList(TileSet.loadLineOfTiles(new Image(animationSet),line,tileSizeX,tileSizeY));
	}
	public Animation(double duration,String animationSet,int line,int tileSizeX,int tileSizeY,boolean mirror) {
		//TODO mirrored animation.
	}
	
	public Animation(String images,int line,int tileSizeX,int tileSizeY) {
		this(-1, images,line,tileSizeX,tileSizeY);
	}
	
	public List<Image> getFrames() {
		return frames;
	}
	
	public Image getCurrentFrame() {
		return frames.get(counter);
	}
	
	public void reset() {
		this.counter = 0;
	}
	
	public void update(double timeDifference) {
		if(duration > -1) {
			if (timeAcc > duration) {
				counter++;
				if (counter >= frames.size()) {
					counter = 0;
				}
				timeAcc = 0;
			}
			timeAcc += timeDifference;
		}

	}
	
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public static Map<Direction, Animation> updatadeMapDuration(Map<Direction, Animation> animations,double duration) {
		Map<Direction, Animation> toReturn = animations;
		toReturn.values().forEach(e -> {
			e.duration = duration;
		});
		return toReturn;
	}

}
