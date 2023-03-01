package nx.engine.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import nx.engine.Game;

/**
 * Loads and manages the tilesets
 */
public class TileSetManager {

	/**
	 * Loads an image from a {@link TileSet}
	 * @param tileSet Tileset to load the image from
	 * @param id Id of the tile
	 * @param width Width of the tile
	 * @param height Height of the tile
	 * @return Image loaded from the tileset
	 */
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

	/**
	 * Loads images from a tileset
	 * @param tileSet Tileset to load the images from
	 * @param witdh Width of the images
	 * @param heigh Height of the images
	 * @return Array of images loaded
	 */
	public static Image[] loadTiles(Image tileSet,int witdh,int heigh) {
		
		ArrayList<Image> tiles = new ArrayList<>();
		
		for(int i = 0; i < tileSet.getHeight() ; i+= heigh) {
			for(int j = 0; j < tileSet.getWidth() ; j+= witdh) {
				WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), j, i, witdh, heigh);
				tiles.add(croppedImage);
			}
		}
		
		return tiles.toArray(new Image[0]);
	}

	/**
	 * Load a line of tiles from a tileset
	 * @param tileSet Tileset to load from
	 * @param line Line to load
	 * @param tileSizeX Width of the tiles
	 * @param tileSizeY Height of the tiles
	 * @return Array of images loaded
	 */
	public static Image[] loadLineOfTiles(Image tileSet,int line,int tileSizeX,int tileSizeY) {
		
		ArrayList<Image> tiles = new ArrayList<Image>();
		
		for(int i = 0; i < tileSet.getWidth() ; i+= tileSizeX) {
			WritableImage croppedImage = new WritableImage(tileSet.getPixelReader(), i, line * tileSizeY, tileSizeX, tileSizeY);
			tiles.add(croppedImage);
		}
		
		return tiles.toArray(new Image[0]);
	}

}
