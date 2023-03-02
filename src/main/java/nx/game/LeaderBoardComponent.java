package nx.game;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import io.github.fvarrui.globalstats.GlobalStats;
import io.github.fvarrui.globalstats.model.Rank;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import nx.util.Global_stats;
import nx.util.StopWatch;

public class LeaderBoardComponent extends VBox implements Initializable {
	
	private ListProperty<Rank> ranks = new SimpleListProperty<>(FXCollections.observableArrayList());

	
    @FXML
    private TableView<Rank> leadersTavbleView;

    @FXML
    private TableColumn<Rank, String> timeTableCollumn;

    @FXML
    private TableColumn<Rank, String> userTableCollumn;

    @FXML
    private VBox view;
	
	public LeaderBoardComponent() {
		super();
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Leaderboard.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			Global_stats.getLeaderboard().forEach(e -> {
				ranks.add(e);
			});
			
			leadersTavbleView.itemsProperty().bind(ranks);
			
			userTableCollumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rank, String>, ObservableValue<String>>() {
			    @Override
			    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rank, String> param) {
			        Rank rank = param.getValue();
			        return new SimpleStringProperty(rank.getName());
			    }
			});
			timeTableCollumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rank, String>, ObservableValue<String>>() {
			    @Override
			    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rank, String> param) {
			        Rank rank = param.getValue();
			        return new SimpleStringProperty(StopWatch.toString(rank.getValue()));
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public VBox getView() {
		return this.view;
	}

}
