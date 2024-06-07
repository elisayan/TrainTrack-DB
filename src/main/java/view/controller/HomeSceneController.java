package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomeSceneController extends AbstractSceneController {
    @FXML
    private Button loginButton;

    @FXML
    private HBox hBox;

    @FXML
    private VBox vBox;

    @FXML
    private Button homeButton;

    @FXML
    private Button purchaseButton;

    @FXML
    private Button timetableButton;

    @FXML
    private Button rankingButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }
}
