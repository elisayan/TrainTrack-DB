package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RankingSceneController extends AbstractSceneController{

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
    private void loginClicked(){
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void journeyClicked(){
        this.view.switchScene("journeyRanking.fxml");
    }

    @FXML
    private void expensesClicked(){
        this.view.switchScene("expensesRanking.fxml");
    }
    
}
