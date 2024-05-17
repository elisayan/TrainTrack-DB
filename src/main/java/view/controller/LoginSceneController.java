package view.controller;

import java.util.*;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Person;
import view.View;

public class LoginSceneController extends AbstractSceneController {

    @FXML
    private Button login;

    @FXML
    private Button home;

    @FXML
    private Button signButton;

    @FXML
    private AnchorPane pane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    private LoginController controller;

    private Map<TextField, PasswordField> loginMap;

    @FXML
    public void loginClicked() {
        this.view.switchScene("home.fxml");
    }

    @FXML
    public void signClicked() {
        this.view.switchScene("signup.fxml");
    }

    public void init(final Optional<Person> person) {
        controller = new LoginController(this);

        final TextField emailField = new TextField();
        final PasswordField passwordField = new PasswordField();

        loginMap = new HashMap<>();

        loginMap.put(emailField, passwordField);
    }

    @FXML
    public void loginUser() {

    }

    public void goForeward(final Person person){
        this.getController().savePerson(person);
        this.getController().goToTheNextDataScene("home.fxml");
    }
}
