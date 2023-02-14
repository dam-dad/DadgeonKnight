package nx.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nx.util.SoundMixer;

public class App extends Application {

	public static Stage mainStage;
	public static Stage secondStage;

	public static MenuController menuController;

	public static SoundMixer mixer = new SoundMixer();

	@Override
	public void start(Stage stage) throws Exception {

		App.mainStage = stage;
		App.secondStage = stage;

		menuController = new MenuController();

		mixer.setMusic("xDeviruchi - Title Theme .wav").setLoop(true).fadeIn(0, 0.05, 2);

		stage.setResizable(false);
		stage.setTitle("Dadgeon Knight");
		stage.getIcons().add(new Image("/assets/icons/helmet-16x16.png"));
		stage.getIcons().add(new Image("/assets/icons/helmet-32x32.png"));
		stage.getIcons().add(new Image("/assets/icons/helmet-64x64.png"));
		stage.setScene(new Scene(menuController.getView()));
		stage.show();

	}

}
