package nx.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MenuController implements Initializable {

	// view
	@FXML
	private Button exitButton, fameButton, playButton, settingsButton;

	@FXML
	private GridPane view;

	public MenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void onExitAction(ActionEvent event) {

	}

	@FXML
	void onHallOfFameAction(ActionEvent event) {

	}

	@FXML
	void onPlayAction(ActionEvent event) {

	}

	@FXML
	void onSettingsAction(ActionEvent event) {

	}

	public GridPane getView() {
		return view;
	}

}
