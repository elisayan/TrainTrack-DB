package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RankingHomeSceneController extends AbstractSceneController {

    @FXML
    private Button expensesButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button journeyButton;

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
    private void expensesClicked() {
        this.view.switchScene("expenseRanking.fxml");
    }
}
