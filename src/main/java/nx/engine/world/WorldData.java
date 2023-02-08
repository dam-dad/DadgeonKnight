package nx.engine.world;

import nx.engine.tile.TileSet;

public enum WorldData {

    START_LEVEL(
            TileSet.WORLD_DARK_TILES,
            "/assets/levels/startedMap/StartedLevel_Entities.csv",
            "/assets/levels/startedMap/StartMap_map.csv",
            "/assets/levels/startedMap/StartMap_detail.csv",
            "/assets/levels/startedMap/StartMap_collitions.csv"
    ),
    DUNGEON(
            TileSet.DANGEON_TILES,
            "/assets/levels/level1/DungeonLevel_Entities.csv",
            "/assets/levels/level1/DungeonLevel_Mapa.csv",
            "/assets/levels/level1/DungeonLevel_Collitions.csv"
    ),
    SECRET(
            TileSet.SECRET_TILES,
            "/assets/levels/secret/entities.csv",
            "/assets/levels/secret/Secret_Floor.csv",
            "/assets/levels/secret/Secret_Collisions.csv"
    );

    private final TileSet tileSet;
    private final String entities;
    private final String[] mapLayers;

    WorldData(TileSet tileSet, String entities, String... mapLayers) {
        this.tileSet = tileSet;
        this.entities = entities;
        this.mapLayers = mapLayers;
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

}
