package view.controller;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.View;

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

    @FXML
    private void rankingClicked(){
        this.view.switchScene("rankingHome.fxml");
    }

    @FXML
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        if (controller.getCurrentPerson() != null) {
            welcomeLabel.setText("Hello " + controller.getCurrentPerson().getName() + "!");
        }
    }
}