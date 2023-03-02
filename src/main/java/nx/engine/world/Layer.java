package nx.engine.world;

import nx.engine.tile.Tile;
import nx.util.CSV;

/**
 * Represents a layer of the level
 */
public class Layer {

    private int layerWidth, layerHeight;
    private Level level;

    private final int[][] tiles;

    /**
     * Constructor
     * @param tiles Tiles the layer has
     */
    public Layer(int[][] tiles) {
        this.tiles = tiles;
        
        
        this.layerWidth = tiles.length;
        this.layerHeight = tiles[0].length;
    }

    /**
     * Constructor
     * @param width Layer width
     * @param height Layer height
     */
    public Layer(int width,int height) {
    	this.tiles = new int[height][width];

    	this.layerHeight = height;
    	this.layerWidth = width;
    	
    	for (int[] row : tiles) {
    	    for (int value : row) {
    	    	value = -1;
    	    }
    	}
	}

    /**
     * Sets the level this layer belongs to
     * @param level
     */
    public void setLevel(Level level) {
        this.level = level;
        level.setMapSize(layerWidth, layerHeight);
    }

    public int getLayerWidth() {
        return layerWidth;
    }

    public int getLayerHeight() {
        return layerHeight;
    }

    public int[][] getTilesValues() {
        return tiles;
    }

    /**
     * Returns the tiles of the layer
     * @return
     */
    public Tile[][] getTiles() {
    	int[][] values = getTilesValues();
    	Tile[][] toReturn = new Tile[values.length][values[0].length];
    	
    	
    	for(int i = 0; i < values.length; i++) {
        	for(int j = 0; j < values[0].length; j++) {
        		toReturn[i][j] = new Tile(i,j,values[i][j] != -1);
        	}
    	}
    	
    	return toReturn;
    }

    /**
     * Loads an array of layers from an array of files
     * @param fileNames Files to load the layers from
     * @return Array of layers loaded
     */
    public static Layer[] loadLayersFromFiles(String... fileNames) {
        Layer[] layers = new Layer[fileNames.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(CSV.loadMapValues(fileNames[i]));
        }

        return layers;
    }

}
