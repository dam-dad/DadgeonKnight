package nx.engine.world;

import nx.engine.tile.TileSet;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public enum WorldData {

    START_LEVEL(
            "start",
            new Vector2D(26,25),
            TileSet.WORLD_DARK_TILES,
            "/assets/levels/startedMap/StartedLevel_Entities.csv",
            "/assets/levels/startedMap/StartMap_map.csv",
            "/assets/levels/startedMap/StartMap_detail.csv",
            "/assets/levels/startedMap/StartMap_collitions.csv"
    ),
    DUNGEON(
            "dungeon",
            new Vector2D(10,10),
            TileSet.DANGEON_TILES,
            "/assets/levels/level1/DungeonLevel_Entities.csv",
            "/assets/levels/level1/DungeonLevel_Mapa.csv",
            "/assets/levels/level1/DungeonLevel_Collitions.csv"
    ),
    SECRET(
            "secret",
            new Vector2D(5,5),
            TileSet.SECRET_TILES,
            "/assets/levels/secret/entities.csv",
            "/assets/levels/secret/Secret_Floor.csv",
            "/assets/levels/secret/Secret_Collisions.csv"
    );

    private final String name;
    private Vector2D spawn;
    private final TileSet tileSet;
    private final String entities;
    private final String[] mapLayers;

    WorldData(String name,Vector2D spawn, TileSet tileSet, String entities, String... mapLayers) {
    	this.spawn = spawn;
        this.name = name;
        this.tileSet = tileSet;
        this.entities = entities;
        this.mapLayers = mapLayers;
    }

    public String getName() {
        return name;
    }
    public Vector2D getSpawn() {
        return spawn;
    }

    public String getEntities() {
        return entities;
    }

    public String[] getMapLayers() {
        return mapLayers;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public static WorldData getByName(String name) {
        return Arrays.stream(values()).filter(worldData -> worldData.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

}
