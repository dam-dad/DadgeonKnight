package nx.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import nx.util.SoundMixer;

public class MenuController implements Initializable {
	
	private static MenuController instance;
	
	//view

	@FXML
	private Button exitButton, fameButton, playButton, settingsButton;

	@FXML
	private Label pruebaGroup;

	@FXML
	private GridPane view;
	

    @FXML
    private SettingsComponent settingsPane;

	private MenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static MenuController getInstance() {
		return instance == null ? instance = new MenuController() : instance;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		exitButton.setOnMouseEntered(e -> {
			App.mixer.addGameSound("text_regular.mp3").play();
		});
		fameButton.setOnMouseEntered(e -> {
			App.mixer.addGameSound("text_regular.mp3").play();
		});
		playButton.setOnMouseEntered(e -> {
			App.mixer.addGameSound("text_regular.mp3").play();
		});
		settingsButton.setOnMouseEntered(e -> {
			App.mixer.addGameSound("text_regular.mp3").play();
		});
	}

	@FXML
	void onExitAction(ActionEvent event) {
		App.mainStage.close();
	}

	@FXML
	void onHallOfFameAction(ActionEvent event) {

	}

	@FXML
	void onPlayAction(ActionEvent event) {
		App.mainStage.setScene(new Scene(GameController.getInstance().getView()));
		App.mixer.setMusic("xDeviruchi - Title Theme .wav").setLoop(true).fadeIn(0,SoundMixer.MUSIC_VOLUME, 2);
		
		App.onMenu = false;
	}

	@FXML
	void onSettingsAction(ActionEvent event) throws IOException {
		if(settingsPane.isVisible()) {
			settingsPane.setVisible(false);
			settingsPane.setDisable(true);
		}else {
			settingsPane.setVisible(true);
			settingsPane.setDisable(false);
			settingsPane.lastGameVolume = settingsPane.inSettingsGameVolume.get();
			settingsPane.lastMusicVolume = settingsPane.inSettingsMusicVolume.get();
		}
	}

	public GridPane getView() {
		return view;
	}

}
