package nx.engine.scenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import nx.engine.Game;
import nx.engine.TextAnimation;
import nx.game.App;
import nx.game.GameController;
import nx.game.MenuController;
import nx.util.CSV;
import nx.util.Global_stats;

public class FinalScene implements nx.engine.scenes.Scene {

	private TextAnimation animation;

	private String id;
	public static String username = "";

	public static String time;
	private long timeNano;

	private boolean registered = false;

	private File file = new File(FinalScene.class.getResource("/utils/text/idPlayer.txt").getPath());

	public FinalScene(String text) {
		animation = new TextAnimation(Arrays.asList(text));

		time = Game.get().stopWatch.toString();
		timeNano = Game.get().stopWatch.elapsed();

		username = "";

		animation.play();
	}

	@Override
	public void update(double delta) {

		if (!registered) {
			for (KeyCode keyCode : Game.inputHandler.getActiveKeys()) {
				if (keyCode.equals(KeyCode.ENTER)) {
					String id = Global_stats.CreateNewUserStatistics(username, timeNano);
					App.mainStage.close();
				}
				if (keyCode.equals(KeyCode.BACK_SPACE)) {
					if (username.length() > 0)
						username = username.substring(0, username.length() - 1);
				}
				if (keyCode.equals(KeyCode.SPACE)) {
					username += " ";
				}
				if (Character.isLetterOrDigit(keyCode.getChar().charAt(0))) {
					username += keyCode.getChar();
				}

				break;
			}
			Game.inputHandler.ClearActiveKeys();
		}

		animation.update(delta);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFont(Game.fontBIG);
		gc.setFill(Color.WHITESMOKE);
		gc.fillText(time, Game.SCREEN_CENTER_X - ((time.length() / 2) * Game.fontBIG.getSize()), 100);
		gc.setFont(Game.font);
		gc.fillText(animation.getCurrentFrame(),
				Game.SCREEN_CENTER_X - ((animation.getCurrentLine().length() / 2) * Game.font.getSize()),
				200 + Game.font.getSize());
		gc.fillText(username, Game.SCREEN_CENTER_X - ((animation.getCurrentLine().length() / 2) * Game.font.getSize()),
				Game.SCREEN_CENTER_Y);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
