package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PassengerDetailSceneController extends AbstractSceneController {

    @FXML
    private TextField addressField;

    @FXML
    private TextField cfField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private ImageView homeButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private ImageView userButton;



    @FXML
    private void confirmClicked() {
        //verifica se le info sono inserite correttamente
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }
}
