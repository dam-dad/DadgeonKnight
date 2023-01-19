package nx.engine.tile;

import java.nio.file.Paths;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Game;
import nx.util.CSV;

public class TileManager {

	// Map settings

	public static int maxWorldCol = 20;
	public static int maxWorldRow = 20;
	public static int worldWidth = Game.tileSize * maxWorldCol;
	public static int worldHeigth = Game.tileSize * maxWorldRow;

	private Game g;
	private TileSet tileSet;
	private Tile[][] mapTiles;
	private int[][] details;

	public TileManager(Game g) {
		this.g = g;
		
//		tileSet = new TileSet("/assets/textures/levels/WorldTiles.png");
//		mapTiles = loadMap("/assets/levels/level1.csv");
		
		tileSet = new TileSet("/assets/textures/levels/DungeonTiles.png");
		
//		mapTiles = loadMapTiles("/assets/levels/dungeon/DungeonLevel_Mapa.csv",
//								"/assets/levels/dungeon/DungeonLevel_Collitions.csv");
//
//		details = loadMapValues("/assets/levels/dungeon/DungeonLevel_Entidades.csv");
	}
	
	public Tile[][] loadMapTiles(String mapa,String collition) {
		int[][] mapTiles;
		int[][] collitions;
		
		mapTiles = loadMapValues(mapa);
		collitions = loadMapValues(collition);
		
		Tile[][] tileSetMap = new Tile[mapTiles.length][mapTiles[0].length];
		
//		for(int i = 0; i < tileSetMap.length; i++) {
//			for(int j = 0; j < tileSetMap[0].length; j++) {
//				boolean c = collitions[j][i] != -1 ? true : false;
//				tileSetMap[j][i] = new Tile(
//						tileSet.getTiles()[mapTiles[j][i]],
//						i * Game.tileSize,
//						j * Game.tileSize,
//						c);
//			}
//		}
		
		return tileSetMap;
	}
	
	public static int[][] loadMapValues(String url) {
		try {
			List<String[]> mapCSV =  CSV.readAllLines(Paths.get(CSV.class.getResource(url).toURI()));
			
			int[][] mapValue = new int[mapCSV.size()][mapCSV.get(0).length];
			
			TileManager.maxWorldCol = mapCSV.get(0).length;
			TileManager.maxWorldRow = mapCSV.size();
			worldWidth = Game.tileSize * maxWorldCol;
			worldHeigth = Game.tileSize * maxWorldRow;
			
			for(int i = 0; i < mapValue.length; i++) {
				for(int j = 0; j < mapValue[0].length; j++) {
					mapValue[i][j] = Integer.parseInt(mapCSV.get(i)[j]);
				}
			}
			return mapValue;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Tile[][] getMapTiles(){
		return this.mapTiles;
	}

}
