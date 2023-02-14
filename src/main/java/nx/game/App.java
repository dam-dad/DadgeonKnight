package nx.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nx.util.SoundMixer;

public class App extends Application {

	public static Stage mainStage;

	
	private MenuController menuController;
	
	public static SoundMixer mixer = new SoundMixer();

	@Override
	public void start(Stage stage) throws Exception {

		App.mainStage = stage;

		
		menuController = new MenuController();
		
		mixer.setMusic("xDeviruchi - Title Theme .wav").setLoop(true).fadeIn(0,0.05,2);

		stage.setResizable(false);
		stage.setTitle("Dadgeon Knight");
		stage.setScene(new Scene(menuController.getView()));
		stage.show();

	}

}
