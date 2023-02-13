package nx.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage mainStage;
	public static Stage secondStage;

	private GameController controller;
	private MenuController menuController;

	@Override
	public void start(Stage stage) throws Exception {

		App.mainStage = stage;
		App.secondStage = stage;

		controller = new GameController();
		menuController = new MenuController();

		stage.setResizable(false);
		stage.setTitle("Dadgeon Knight");
		stage.setScene(new Scene(menuController.getView()));
		stage.show();

	}

}
