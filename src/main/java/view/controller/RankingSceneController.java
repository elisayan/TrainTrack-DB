package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Journey;

public class RankingSceneController extends AbstractSceneController {

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button journeyButton;

    @FXML
    private Button expensesButton;

    @FXML
    private BorderPane pane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private Label delaysLabel;

    @FXML
    private TableView<Journey> table;

    @FXML
    private TableColumn<Journey, Integer> delaysJourneyId;

    @FXML
    private TableColumn<Journey,String> delaysDeparture;

    @FXML
    private TableColumn<Journey,String> delaysArrival;

    @FXML
    private TableColumn<Journey,Integer> delaysAverage;

    @FXML
    private Label earlyLabel;

    @FXML
    private TableColumn<Journey, Integer> earlyJourneyId;

    @FXML
    private TableColumn<Journey,String> earlyDeparture;

    @FXML
    private TableColumn<Journey,String> earlyArrival;

    @FXML
    private TableColumn<Journey,Integer> earlyAverage;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void journeyClicked() {
        this.view.switchScene("journeyRanking.fxml");
    }

    @FXML
    private void expensesClicked() {
        this.view.switchScene("expensesRanking.fxml");
    }

}
