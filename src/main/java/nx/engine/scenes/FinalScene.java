package nx.engine.scenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import nx.engine.Game;
import nx.engine.TextAnimation;
import nx.util.CSV;
import nx.util.Global_stats;

public class FinalScene implements Scene{
	
	private final TextAnimation animation;
	
	private String id;
	private String username =  "";
	
	private String time;
	private long timeNano;
	
	private File file = new File(FinalScene.class.getResource("/utils/text/idPlayer.txt").getPath());
	
	public FinalScene(String text) {
		animation = new TextAnimation(Arrays.asList(text));
		animation.play();
		
		time = Game.get().stopWatch.toString();
		timeNano = Game.get().stopWatch.elapsed();
		
		username = "";
	}


	@Override
	public void update(double delta) {
		
		for (KeyCode keyCode : Game.inputHandler.getActiveKeys()) {
			if(keyCode.equals(KeyCode.ENTER)) {
				if(getUserID(file.getAbsolutePath()) != null) {
					id = getUserID(file.getAbsolutePath());
					Global_stats.updateUserStadistic(id, timeNano);
				}else {
					String id = Global_stats.CreateNewUserStatistics(username, timeNano);
					saveID(file.getAbsolutePath(),id);
				}
			}
			if(Character.isLetterOrDigit(keyCode.getChar().charAt(0))) {
				username += keyCode.getChar();
			}
			
			break;
		}
		Game.inputHandler.ClearActiveKeys();
		
		animation.update(delta);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFont(Game.font);
		gc.setFill(Color.WHITESMOKE);
		gc.fillText(time, Game.SCREEN_CENTER_X - ((time.length()/2) * Game.font.getSize()), 100);
		gc.fillText(animation.getCurrentFrame(), Game.SCREEN_CENTER_X - ((animation.getCurrentLine().length()/2) * Game.font.getSize()), 200 + Game.font.getSize());
		gc.fillText(username, Game.SCREEN_CENTER_X - ((animation.getCurrentLine().length()/2) * Game.font.getSize()), Game.SCREEN_CENTER_Y);
	}
	
	public void saveID(String filename, String id) {
	    try {
	        FileWriter fileWriter = new FileWriter(filename);
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(id);
	        bufferedWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public String getUserID(String filename) {
	    try {
	        FileReader fileReader = new FileReader(filename);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        String line = bufferedReader.readLine();
	        String id = null;
	        if (line != null && !line.isEmpty()) {
	            id = line.trim();
	        }
	        bufferedReader.close();
	        return id;
	    } catch (IOException e) {
	        return null;
	    }
	}

}
