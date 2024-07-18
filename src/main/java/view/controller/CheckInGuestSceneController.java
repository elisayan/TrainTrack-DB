package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CheckInGuestSceneController extends AbstractSceneController{

    @FXML
    private TextField emailField;

    @FXML
    private Label messageLabel;

    @FXML
    private void confirmClicked() {
        //controllare se la mail è presente nel database
        //oppure se ha biglietti
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }
}
