package nx.util;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVReader;

public class CSV {
	
	public static List<String[]> readAllLines(Path filePath) throws Exception {

	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}

}
