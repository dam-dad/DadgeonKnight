package nx.engine.tile;

import javafx.scene.image.Image;
import nx.engine.Game;

/**
 * Represents a tileset
 */
public class TileSet {

	public static final TileSet DANGEON_TILES = new TileSet("/assets/textures/levels/DungeonTiles.png", Game.tileSize,Game.tileSize);
	public static final TileSet WORLD_DARK_TILES = new TileSet("/assets/textures/levels/WorldTiles_Dark.png",Game.tileSize, Game.tileSize);
	public static final TileSet ITEMS_TILES = new TileSet("/assets/textures/items/roguelikeitems.png", 16, 16);
	public static final TileSet SECRET_TILES = new TileSet("/assets/textures/levels/Dungeon_Tileset_at.png", 8, 8);
	public static final TileSet LAVA_TILES = new TileSet("/assets/textures/levels/Tile level 4.png", 16, 16);
	public static final TileSet WORLD_BRIDGE_TILES = new TileSet("/assets/textures/levels/floatingDungeon.png",16,16);

	
	private final Image set;
	private final Image[] tiles;
	private final int with,heigh;
	
	

	/**
	 * Constructor
	 * @param s Image URI
	 * @param width Tileset width
	 * @param height Tileset height
	 */
	public TileSet(String s, int with, int heigh) {
		set = new Image(s);

		tiles = TileSetManager.loadTiles(set, with, heigh);
		this.with = with;
		this.heigh = heigh;
	}

	public int getWith() {
		return with;
	}

	public int getHeigh() {
		return heigh;
	}

	public Image getSet() {
		return set;
	}

	public Image[] getTiles() {
		return tiles;
	}

}
