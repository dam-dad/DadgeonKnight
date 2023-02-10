package nx.engine.world;

import nx.util.CSV;

public class Layer {

    private int layerWidth, layerHeight;

    private final int[][] tiles;

    public Layer(String fileName) {
        this.tiles = CSV.loadMapValues(fileName);
        
        Level.setMapSize(tiles.length, tiles[0].length);

        this.layerWidth = tiles.length;
        this.layerHeight = tiles[0].length;
    }
    
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

    public int getLayerWidth() {
        return layerWidth;
    }

    public int getLayerHeight() {
        return layerHeight;
    }

    public int[][] getTiles() {
        return tiles;
    }

}
