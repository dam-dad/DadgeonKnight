package nx.engine.world;

import nx.util.CSV;

public class Layer {

    private int layerWidth, layerHeight;

    private final int[][] tiles;

    public Layer(String fileName) {
        this.tiles = CSV.loadMapValues(fileName);

        this.layerWidth = tiles.length;
        this.layerHeight = tiles[0].length;
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
