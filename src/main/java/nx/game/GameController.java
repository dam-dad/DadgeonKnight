package nx.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import nx.engine.Game;

/**
 * Controlador que gestiona el mapa del juego
 */
public class GameController implements Initializable {
	
	private static GameController instance;

	//Model
	
	private Game game;

	//View
	
	@FXML
	private BorderPane view;
	@FXML
	private Canvas canvas;

	private GameController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static GameController getInstance() {
		return instance == null ? instance = new GameController() : instance;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = Game.get(canvas);
		game.start();
	}

	public BorderPane getView() {
		return view;
	}

	public Game getGame() {
		return game;
	}

}
