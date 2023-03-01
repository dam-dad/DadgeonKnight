package nx.engine.world;

import nx.engine.tile.TileSet;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents the data to load a world
 */
public enum WorldData {

	START_LEVEL("start",
			new Vector2D(26, 25),
			TileSet.WORLD_DARK_TILES,
			"/assets/levels/startedMap/StartedLevel_Entities.csv",
			Layer.loadLayersFromFiles(
					"/assets/levels/startedMap/StartMap_map.csv",
					"/assets/levels/startedMap/StartMap_detail.csv",
					"/assets/levels/startedMap/StartMap_collitions.csv"
					)
			),
	
	DUNGEON("dungeon",
			new Vector2D(10, 10),
			TileSet.DANGEON_TILES,
			"/assets/levels/level1/DungeonLevel_Entities.csv",
			Layer.loadLayersFromFiles(
					"/assets/levels/level1/DungeonLevel_Mapa.csv",
					"/assets/levels/level1/DungeonLevel_Collitions.csv"
					)
			),
	SECRET("secret",
			new Vector2D(5, 5),
			TileSet.SECRET_TILES,
			"/assets/levels/secret/entities.csv",
			Layer.loadLayersFromFiles(
					"/assets/levels/secret/Secret_Floor.csv",
					"/assets/levels/secret/Secret_Collisions.csv"
					)
			);
//	BRIDGE("bridge",
//			new Vector2D(0, 0),
//			TileSet.WORLD_BRIDGE_TILES,
//			"/assets/levels/level3/DungeonBridgeLevel_entities.csv",
//			Layer.loadLayersFromFiles(
//					"/assets/levels/level3/map.csv",
//					"/assets/levels/level3/DungeonBridgeLevel_details.csv",
//					"/assets/levels/secret/Secret_collitions.csv"
//					)
//			);

	private final String name;
	private Vector2D spawn;
	private final TileSet tileSet;
	private final String entities;
	private final Layer[] mapLayers;

	/**
	 * Constructor
	 * @param name Name of the level
	 * @param spawn Player spawn position
	 * @param tileSet Tileset of the level
	 * @param entities String to load the entities from
	 * @param mapLayers Layers of the map
	 */
	WorldData(String name, Vector2D spawn, TileSet tileSet, String entities, Layer... mapLayers) {
		this.spawn = spawn;
		this.name = name;
		this.tileSet = tileSet;
		this.entities = entities;
		this.mapLayers = mapLayers;
	}

	public Vector2D getSpawn() {
		return this.spawn;
	}

	public String getName() {
		return name;
	}

	public String getEntities() {
		return entities;
	}

	public Layer[] getMapLayers() {
		return mapLayers;
	}

    /**
     * Returns a world data by its name
     * @param name Name of the world
     * @return WorldData of the world specified
     */
    public static WorldData getByName(String name) {
        return Arrays.stream(values()).filter(worldData -> worldData.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

	public TileSet getTileSet() {
		return tileSet;
	}

}
