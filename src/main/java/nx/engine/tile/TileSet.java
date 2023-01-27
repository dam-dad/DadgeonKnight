package nx.engine.tile;

import javafx.scene.image.Image;
import nx.engine.Game;

public class TileSet {
	
	public static final TileSet DANGEON_TILES = new TileSet("/assets/textures/levels/DungeonTiles.png",Game.tileSize,Game.tileSize);
	public static final TileSet ITEMS_TILES = new TileSet("/assets/textures/items/roguelikeitems.png",16,16);
	
	private final String  uri;
	private Image set;
	
	private Image[] tiles;
	
	public TileSet(String s,int with,int heigh) {
		this.uri = s;
		set = new Image(s);
		
		tiles = TileSetManager.loadTiles(set,with,heigh);
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
