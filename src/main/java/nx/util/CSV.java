package nx.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import nx.engine.Game;
import nx.engine.world.Level;

/**
 * Utility class to deal with CSV files
 */
public class CSV {

	/**
	 * Parses a CSV file to a list of strings
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readAllLines(InputStream is) throws Exception {

	    try (Reader reader = new InputStreamReader(is)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}

	/**
	 * Parses a CSV file to a list of strings
	 * @return
	 * @throws Exception
	 */
	public static List<String> readAllLinesTogether(InputStream is) throws Exception {
	    List<String> lines = new ArrayList<>();	
	    try (Reader reader = new InputStreamReader(is)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            List<String[]> csvLines = csvReader.readAll();
	            for (String[] line : csvLines) {
	                lines.add(String.join(",", line));
	            }
	        }
	    }
	    return lines;
	}

	/**
	 * Loads a map from a CSV file
	 * @param url File to load from
	 * @return Tile data
	 */
	public static int[][] loadMapValues(String url) {
		try {
			List<String[]> mapCSV =  CSV.readAllLines(CSV.class.getResourceAsStream(url));

			int[][] mapValue = new int[mapCSV.size()][mapCSV.get(0).length];

//			Level.maxWorldCol = mapCSV.get(0).length;
//			Level.maxWorldRow = mapCSV.size();
//			Level.worldWidth = Game.tileSize * Level.maxWorldCol;
//			Level.worldHeigth = Game.tileSize * Level.maxWorldRow;

			for(int i = 0; i < mapValue.length; i++) {
				for(int j = 0; j < mapValue[0].length; j++) {
					mapValue[j][i] = Integer.parseInt(mapCSV.get(i)[j]);
				}
			}
			return mapValue;
		} catch (Exception e) {
			return null;
		}
	}

}
