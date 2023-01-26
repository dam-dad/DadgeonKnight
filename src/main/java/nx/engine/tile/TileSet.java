package nx.engine.tile;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import nx.engine.Game;


public class TileSet {
	
	public static final String  DANGEON_TILES= "/assets/textures/levels/DungeonTiles.png";
	
	private Image set;
	public static Image[] tiles;
	
	public TileSet(String image) {
		set = new Image(image);
		setTiles(loadTiles(set,0));
	}

	public static void loadTiles(String image) {
		TileSet.tiles = loadTiles(new Image(image),0);
	}
	
	public static Image loadImageFromTileSet(Image tileSet,int id,int width,int height) {
		int n = 0;
		for(int i = 0; i < tileSet.getWidth() ; i+= Game.tileSize) {
			for(int j = 0; j < tileSet.getHeight() ; j+= Game.tileSize) {
				if(n == id) {
					return new WritableImage(tileSet.getPixelReader(), j, i, width, height);
				}
				n++;
				
			}
		}
		return null;
	}

	private static Image[] loadTiles(Image tileSet,int spacing) {
		
		ArrayList<Image> tiles = new ArrayList<>();
		
		for(int i = 0; i < tileSet.getWidth() ; i+= Game.tileSize + spacing) {
			for(int j = 0; j < tileSet.getHeight() ; j+= Game.tileSize + spacing) {
				WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), j, i, Game.tileSize, Game.tileSize);
				tiles.add(croppedImage);
			}
		}
		
		return tiles.toArray(new Image[tiles.size()]);
	}
	
	public static Image[]  loadLineOfTiles(Image tileSet,int line,int tileSizeX,int tileSizeY) {
		
		ArrayList<Image> tiles = new ArrayList<Image>();
		
		for(int i = 0; i < tileSet.getWidth() ; i+= tileSizeX) {
			WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), i, line * tileSizeY, tileSizeX, tileSizeY);
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
