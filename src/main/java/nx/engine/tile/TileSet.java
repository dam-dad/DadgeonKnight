package nx.engine.tile;

import javafx.scene.image.Image;
import nx.engine.Game;

public class TileSet {
	
	public static final TileSet DANGEON_TILES = new TileSet("/assets/textures/levels/DungeonTiles.png",Game.tileSize,Game.tileSize);
	public static final TileSet ITEMS_TILES = new TileSet("/assets/textures/items/roguelikeitems.png",16,16);
	public static final TileSet SECRET_TILES = new TileSet("/assets/textures/levels/Dungeon_Tileset_at.png", 8, 8);
	
	private final String  uri;
	private Image set;
	
	private Image[] tiles;
	
	private int with,heigh;
	
	public TileSet(String s,int with,int heigh) {
		this.uri = s;
		set = new Image(s);
		
		tiles = TileSetManager.loadTiles(set,with,heigh);
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

	public void setSet(Image set) {
		this.set = set;
	}

	public Image[] getTiles() {
		return tiles;
	}

	public void setTiles(Image[] tiles) {
		this.tiles = tiles;
	}

	public String getUri() {
		return uri;
	}
	
	
	

}
