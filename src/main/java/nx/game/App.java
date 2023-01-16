package nx.game;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static Stage mainStage;
	
	private GameController controller;

	@Override
	public void start(Stage stage) throws Exception {

		App.mainStage = stage;
		
		controller = new GameController();
		
		stage.setResizable(false);
		stage.setTitle("NX-Test");
		stage.setScene(new Scene(controller.getView()));
		stage.show();
		
		

		


	}

}
