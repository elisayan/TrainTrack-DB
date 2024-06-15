package view.controller;

import db.ThroughTable;
import model.DelayInfo;
import model.EarlyInfo;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class JourneyRankingSceneController extends AbstractSceneController {

    @FXML
    private TableColumn<DelayInfo, String> delayDestinationColumn;

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
    private TableColumn<EarlyInfo, String> earlyDestinationColumn;

    @FXML
    private TableColumn<EarlyInfo, Float> earlyAverageColumn;

    @FXML
    private TableColumn<EarlyInfo, String> earlyDepartureColumn;

    @FXML
    private TableColumn<EarlyInfo, String> earlyJourneyIDColumn;

    @FXML
    private Label earlyLabel;

    @FXML
    private TableColumn<EarlyInfo, Integer> earlyRankingColumn;

    @FXML
    private TableView<EarlyInfo> earlyTable;

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
        this.delayDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("stazioneDestinazioneNome"));
        this.delayAverageColumn.setCellValueFactory(new PropertyValueFactory<>("mediaMinutiRitardo"));
        this.delayRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        this.delayAverageColumn.setCellFactory(column -> new FormattedTableCell<>());

        this.earlyJourneyIDColumn.setCellValueFactory(new PropertyValueFactory<>("codPercorso"));
        this.earlyDepartureColumn.setCellValueFactory(new PropertyValueFactory<>("stazionePartenzaNome"));
        this.earlyDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("stazioneDestinazioneNome"));
        this.earlyAverageColumn.setCellValueFactory(new PropertyValueFactory<>("mediaMinutiAnticipo"));
        this.earlyRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        this.earlyAverageColumn.setCellFactory(column -> new FormattedTableCell<>());

        populateDelayTable();
        populateEarlyTable();
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

    private void populateEarlyTable() {
        List<EarlyInfo> earlyInfos = getEarlyInfos();
        earlyTable.getItems().setAll(earlyInfos);
    }

    private List<EarlyInfo> getEarlyInfos() {
        ThroughTable throughTable = new ThroughTable();
        List<EarlyInfo> early = throughTable.topFiveEarlyJourney();

        for (int i = 0; i < early.size(); i++) {
            early.get(i).setRank(i + 1);
        }

        return early;
    }
}
