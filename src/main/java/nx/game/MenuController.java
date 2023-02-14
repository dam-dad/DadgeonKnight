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

public class MenuController implements Initializable {

	// view
	private SettingsComponent settingsComponent;

	@FXML
	private Button exitButton, fameButton, playButton, settingsButton;

	@FXML
	private Label pruebaGroup;

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
		settingsComponent = new SettingsComponent();

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
	}

	@FXML
	void onSettingsAction(ActionEvent event) throws IOException {
//		App.mainStage.getScene().setRoot(settingsComponent);
	}

	public GridPane getView() {
		return view;
	}

}
