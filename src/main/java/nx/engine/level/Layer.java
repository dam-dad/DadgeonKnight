package nx.engine.level;

import nx.util.CSV;

public class Layer {

    private int layerWidth, layerHeight;

    private final int[][] tiles;

    public Layer(String fileName) {
        this.tiles = CSV.loadMapValues(fileName);
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
