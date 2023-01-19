package nx.util;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVReader;
import nx.engine.Game;
import nx.engine.world.Level;
import nx.engine.tile.TileManager;

public class CSV {
	
	public static List<String[]> readAllLines(Path filePath) throws Exception {

	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}

	public static int[][] loadMapValues(String url) {
		try {
			List<String[]> mapCSV =  CSV.readAllLines(Paths.get(CSV.class.getResource(url).toURI()));

			int[][] mapValue = new int[mapCSV.size()][mapCSV.get(0).length];

			TileManager.maxWorldCol = mapCSV.get(0).length;
			TileManager.maxWorldRow = mapCSV.size();
			Level.worldWidth = Game.tileSize * Level.maxWorldCol;
			Level.worldHeigth = Game.tileSize * Level.maxWorldRow;

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

}
