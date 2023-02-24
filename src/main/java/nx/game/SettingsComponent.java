package nx.game;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.sound.midi.SoundbankResource;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import nx.util.Music;
import nx.util.SoundMixer;

public class SettingsComponent extends GridPane implements Initializable {

	// model
	DecimalFormat decimalFormat = new DecimalFormat("#");

	// view
	@FXML
	private Button acceptButton, cancelButton;

	@FXML
	private Slider effectsSlider, musicSlider;

	@FXML
	private Label settingsLabel, effectsLabel, musicLabel;

	public SettingsComponent() {
		super();
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingsView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		musicSlider.valueProperty().addListener(this::changeMusicValue);
		effectsSlider.valueProperty().addListener(this::changeEffectsValue);

		musicSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				SoundMixer.MUSIC_VOLUME = (double) newValue * 0.1;
				if(App.mixer.getMusic() != null) {
					App.mixer.getMusic().setVolume((double) newValue * 0.1);
				}
			}
		});

		effectsSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				SoundMixer.GAME_VOLUME = ((double) newValue) * 0.1;
				if(App.mixer.getGameSounds().size() > 0) {
					App.mixer.getGameSounds().forEach(e -> e.setVolume((double) newValue * 0.01));
				}
			}
		});

	}

	private void changeMusicValue(ObservableValue<? extends Number> o, Number ov, Number nv) {
		String rounded = decimalFormat.format(Double.parseDouble(nv.toString()));
		musicLabel.setText(rounded + "%");
	}

	private void changeEffectsValue(ObservableValue<? extends Number> o, Number ov, Number nv) {
		String rounded = decimalFormat.format(Double.parseDouble(nv.toString()));
		effectsLabel.setText(rounded + "%");
	}
	@FXML
	void onAcceptAction(ActionEvent event) throws IOException {
		MenuController.getInstance().onSettingsAction(event);
	}

	@FXML
	void onCancelAction(ActionEvent event) throws IOException {
		MenuController.getInstance().onSettingsAction(event);
	}

}
