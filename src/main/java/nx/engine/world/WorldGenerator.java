package nx.engine.world;

import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.room.dungeon.DungeonGenerator;

/**
 * Generates a dungeons procedurally
 */
public class WorldGenerator {

    /**
     * Generates the tile data for a specified size
     * @param size Size of the level side
     * @return Tile data of the level generated
     */
    public static int[][] generateDungeonWorldData(int size) {
        final Grid grid = new Grid(size);

        final DungeonGenerator dungeonGenerator = new DungeonGenerator();
        dungeonGenerator.setRoomGenerationAttempts(200);
        dungeonGenerator.setMaxRoomSize(15);
        dungeonGenerator.setTolerance(10);
        dungeonGenerator.setMinRoomSize(7);
        dungeonGenerator.setWindingChance(0.05f);
        dungeonGenerator.generate(grid);

        int[][] tileData = new int[size][size];

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                float value = grid.get(i, j);
                if (value == 0)
                    tileData[i][j] = 12;
                if (value == 0.5)
                    tileData[i][j] = 10;
                if (value == 1)
                    tileData[i][j] = 18;
            }
        }

        return tileData;
    }

    /**
     * Populates the data fed
     * @param data Data to populate
     * @return Data populated
     */
    private static int[][] populateWorldData(int[][] data) {
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length - 1; j++) {
                if (data[i][j] == 10) {
                    if (data[i-1][j] == 18) {
                        data[i-1][j] = 64;
                    }
                }
            }
        }

        return data;
    }

    /**
     * Generates empty collision data
     * @param size Level side size
     * @return Empty tile data
     */
    private static int[][] generateEmptyData(int size) {
        int[][] newData = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newData[i][j] = -1;
            }
        }

        return newData;
    }

    /**
     * Generates a dungeon for the specified size
     * @param size Dungeon size
     * @return Layer array of the generated level
     */
    public static Layer[] generateDungeon(int size) {
        Layer[] layers = new Layer[2];
        int[][] worldData = generateDungeonWorldData(size);
        layers[0] = new Layer(populateWorldData(worldData));
        layers[1] = new Layer(generateEmptyData(size));

        return layers;
    }

}
