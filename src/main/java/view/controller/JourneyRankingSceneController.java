package view.controller;

import db.ThroughTable;
import model.DelayInfo;

import java.util.*;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class JourneyRankingSceneController extends AbstractSceneController {

    @FXML
    private TableColumn<DelayInfo, String> delayArrivalColumn;

    @FXML
    private TableColumn<DelayInfo, Float> delayAverageColumn;

    @FXML
    private TableColumn<DelayInfo, String> delayDepartureColumn;

    @FXML
    private TableColumn<DelayInfo, String> delayJourneyIDColumn;

    @FXML
    private Label delayLabel;

    @FXML
    private TableColumn<DelayInfo, Integer> delayRankingColumn;

    @FXML
    private TableView<DelayInfo> delayTable;

    @FXML
    private TableColumn<?, ?> earlyArrivalColumn;

    @FXML
    private TableColumn<?, ?> earlyAverageColumn;

    @FXML
    private TableColumn<?, ?> earlyDepartureColumn;

    @FXML
    private TableColumn<?, ?> earlyJourneyIDColumn;

    @FXML
    private Label earlyLabel;

    @FXML
    private TableColumn<?, ?> earlyRankingColumn;

    @FXML
    private TableView<?> earlyTable;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void journeyClicked() {
        this.view.switchScene("journeyRanking.fxml");
    }

    @FXML
    public void initialize() {
        this.delayJourneyIDColumn.setCellValueFactory(new PropertyValueFactory<>("codPercorso"));
        this.delayDepartureColumn.setCellValueFactory(new PropertyValueFactory<>("stazionePartenzaNome"));
        this.delayArrivalColumn.setCellValueFactory(new PropertyValueFactory<>("stazioneDestinazioneNome"));
        this.delayAverageColumn.setCellValueFactory(new PropertyValueFactory<>("mediaMinutiRitardo"));
        this.delayRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        populateDelayTable();
    }

    private void populateDelayTable() {
        List<DelayInfo> delayInfos = getDelayInfos();
        delayTable.getItems().setAll(delayInfos);
    }

    private List<DelayInfo> getDelayInfos() {
        ThroughTable throughTable = new ThroughTable();
        List<DelayInfo> delays = throughTable.topFiveDelayJourney();

        for (int i = 0; i < delays.size(); i++) {
            delays.get(i).setRank(i + 1);
        }

        return delays;
    }
}
