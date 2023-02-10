package nx.engine.world;

import nx.util.CSV;

public class Layer {

    private int layerWidth, layerHeight;
    private Level level;

    private final int[][] tiles;

    public Layer(int[][] tiles) {
        this.tiles = tiles;
//        this.tiles = CSV.loadMapValues(fileName);

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

    public int[][] getTiles() {
        return tiles;
    }

    public static Layer[] loadLayersFromFiles(String... fileNames) {
        Layer[] layers = new Layer[fileNames.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(CSV.loadMapValues(fileNames[i]));
        }

        return layers;
    }

}
