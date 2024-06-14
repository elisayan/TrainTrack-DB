package view.controller;

import db.ThroughTable;
import model.DelayInfo;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class JourneyRankingSceneController extends AbstractSceneController {

    @FXML
    private TableColumn<?, ?> delayArrivalColumn;

    @FXML
    private TableColumn<DelayInfo, Float> delayAverageColumn;

    @FXML
    private TableColumn<?, ?> delayDepartureColumn;

    @FXML
    private TableColumn<DelayInfo, String> delayJourneyIDColumn;

    @FXML
    private Label delayLabel;

    @FXML
    private TableColumn<?, ?> delayRankingColumn;

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
        this.delayAverageColumn.setCellValueFactory(new PropertyValueFactory<>("mediaMinutiRitardo"));
        populateDelayTable();
    }

    private void populateDelayTable() {
        List<DelayInfo> delayInfos = getDelayInfos();
        delayTable.getItems().setAll(delayInfos);
    }

    private List<DelayInfo> getDelayInfos() {
        ThroughTable throughTable = new ThroughTable();
        return throughTable.topFiveDelayJourney();
    }
}
