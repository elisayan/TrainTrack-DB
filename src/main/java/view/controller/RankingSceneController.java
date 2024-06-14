package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Journey;

public class RankingSceneController extends AbstractSceneController {

    @FXML
    private Label expenseLabel;

    @FXML
    private TableColumn<?, ?> expenseRankingColumn;

    @FXML
    private TableColumn<?, ?> expensesColumn;

    @FXML
    private TableView<?> expensesTable;

    @FXML
    private TableColumn<?, ?> firstNameColumn;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private Button loginButton;

    @FXML
    private TableColumn<?, ?> delayAverageColumn;

    @FXML
    private TableColumn<?, ?> delayDepartureColumn;

    @FXML
    private TableColumn<?, ?> delayDestinationColumn;

    @FXML
    private TableColumn<?, ?> delayJourneyIDColumn;

    @FXML
    private Label delayLabel;

    @FXML
    private TableColumn<?, ?> delayRankingColumn;

    @FXML
    private TableView<?> delayTable;

    @FXML
    private TableColumn<?, ?> earlyAverageColumn;

    @FXML
    private TableColumn<?, ?> earlyDepartureColumn;

    @FXML
    private TableColumn<?, ?> earlyDestinantionColumn;

    @FXML
    private TableColumn<?, ?> earlyJourneyIDColumn;

    @FXML
    private Label earlyLabel;

    @FXML
    private TableColumn<?, ?> earlyRankingColumn;

    @FXML
    private TableView<?> earlyTable;

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

    @FXML
    private void initializeDelayTable(){
        delayJourneyIDColumn.setCellValueFactory(new PropertyValueFactory<>("journeyID"));
        
    }
}
