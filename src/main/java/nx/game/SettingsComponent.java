package nx.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class SettingsComponent extends GridPane implements Initializable {

	/*
	 * TODO 1. crear componene de los ajustes. 2. añadir componente a un group. 3.
	 * añadir group a la vista principal. 4. usar properties para habilitarlo y
	 * deshabilitarlo.
	 */

	@FXML
	private Button acceptButton, cancelButton;

	@FXML
	private Slider effectsSlider, generalSlider, musicSlider;

	@FXML
	private Label settingsLabel;

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

	}

	@FXML
	void onAcceptAction(ActionEvent event) {

	}

	@FXML
	void onCancelAction(ActionEvent event) {

	}

}
