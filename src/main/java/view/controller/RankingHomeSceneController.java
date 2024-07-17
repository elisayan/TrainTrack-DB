package view.controller;

import javafx.fxml.FXML;

public class RankingHomeSceneController extends AbstractSceneController {

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
