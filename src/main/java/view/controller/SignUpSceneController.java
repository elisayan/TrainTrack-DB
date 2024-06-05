package view.controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Person;

public class SignUpSceneController extends AbstractSceneController {
    @FXML
    private Button signButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField surnameText;

    @FXML
    private TextField codeText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField phoneText;

    @FXML
    private TextField addressText;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private AnchorPane pane;

    @Override
    public void init(Optional<Person> person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    public void signClicked() {
        this.view.switchScene("home.fxml");
    }

}
