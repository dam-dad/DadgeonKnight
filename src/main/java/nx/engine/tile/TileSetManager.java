package nx.engine.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import nx.engine.Game;


public class TileSetManager {
	
	public static Image loadImageFromTileSet(TileSet tileSet,int id,int width,int height) {
		int n = 0;
		for(int i = 0; i < tileSet.getSet().getHeight() ; i+= tileSet.getHeigh()) {
			for(int j = 0; j < tileSet.getSet().getWidth() ; j+= tileSet.getWith()) {
				if(n == id) {
					return new WritableImage(tileSet.getSet().getPixelReader(), j, i, width, height);
				}
				n++;
				
			}
		}
		return null;
	}
	public static Image cropImage(Image tileSet,int id,int width,int height) {
		int n = 0;
		for(int i = 0; i < tileSet.getHeight() ; i+= height) {
			for(int j = 0; j < tileSet.getWidth() ; j+= width) {
				if(n == id) {
					return new WritableImage(tileSet.getPixelReader(), j, i, width, height);
				}
				n++;
				
			}
		}
		return null;
	}

	public static Image[] loadTiles(Image tileSet,int spacing) {
		
		ArrayList<Image> tiles = new ArrayList<>();
		
		for(int i = 0; i < tileSet.getHeight() ; i+= Game.tileSize + spacing) {
			for(int j = 0; j < tileSet.getWidth() ; j+= Game.tileSize + spacing) {
				WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), j, i, Game.tileSize, Game.tileSize);
				tiles.add(croppedImage);
			}
		}
		
		return tiles.toArray(new Image[tiles.size()]);
	}
	
	public static Image[] loadTiles(Image tileSet,int witdh,int heigh) {
		
		ArrayList<Image> tiles = new ArrayList<>();
		
		for(int i = 0; i < tileSet.getHeight() ; i+= heigh) {
			for(int j = 0; j < tileSet.getWidth() ; j+= witdh) {
				WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), j, i, witdh, heigh);
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

	public static Image[] getTiles(TileSet n) {
		return n.getTiles();
	}
}
