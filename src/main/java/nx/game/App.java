package nx.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nx.util.Music;
import nx.util.SoundMixer;

public class App extends Application {
	
	public static Stage mainStage;
	
	private GameController controller;
	
	
	public static SoundMixer mixer = new SoundMixer();

	@Override
	public void start(Stage stage) throws Exception {

		App.mainStage = stage;
		
		mixer.setMusic("xDeviruchi - Title Theme .wav").setLoop(true).fadeIn(0,0.05,2);
		
		controller = new GameController();
		
		stage.setResizable(false);
		stage.setTitle("DADgeon_Knight");
		stage.setScene(new Scene(controller.getView()));
		stage.show();
	}

}
