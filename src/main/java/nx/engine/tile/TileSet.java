package nx.engine.tile;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import nx.engine.Game;

public class TileSet {
	
	private Image set;
	private Image[] tiles;
	
	
	public TileSet() {
		
	}
	
	public TileSet(String image) {
		set = new Image(image);
		setTiles(loadTiles(set,0));
	}
	
	public static Image loadImageFromTileSet(Image tileSet,int id,int width,int height,int spacing) {
		
		int n = 0;
		for(int i = 0; i < tileSet.getWidth() ; i+= Game.tileSize + spacing) {
			for(int j = 0; j < tileSet.getHeight() ; j+= Game.tileSize + spacing) {
				if(n == id) {
					return new WritableImage(tileSet.getPixelReader(), j, i, width, height);
				}
				n++;
				
			}
		}
		return null;
	}

	
	public static Image[]  loadTiles(Image tileSet,int spacing) {
		
		ArrayList<Image> tiles = new ArrayList<Image>();
		
		for(int i = 0; i < tileSet.getWidth() ; i+= Game.tileSize + spacing) {
			for(int j = 0; j < tileSet.getHeight() ; j+= Game.tileSize + spacing) {
				WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), j, i, Game.tileSize, Game.tileSize);
				tiles.add(croppedImage);
			}
		}
		
		return tiles.toArray(new Image[tiles.size()]);
	}
	
	public static Image[]  loadLineOfTiles(Image tileSet,int line,int spacing) {
		
		ArrayList<Image> tiles = new ArrayList<Image>();
		
		for(int i = 0; i < tileSet.getWidth() ; i+= Game.tileSize + spacing) {
			WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), i, line * Game.tileSize, Game.tileSize, Game.tileSize);
			tiles.add(croppedImage);
		}
		
		return tiles.toArray(new Image[tiles.size()]);
	}

	public Image[] getTiles() {
		return tiles;
	}

	public void setTiles(Image[] tiles) {
		this.tiles = tiles;
	}

	public Image getSet() {
		return set;
	}

	public void setSet(Image set) {
		this.set = set;
	}
}
