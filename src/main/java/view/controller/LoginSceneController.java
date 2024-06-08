package view.controller;

import controller.Controller;
import controller.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Person;
import view.View;

public class LoginSceneController extends AbstractSceneController {

    @FXML
    private Button loginButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button signButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    private LoginController controller;

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        this.controller = new LoginController(this, this.getController());
    }

    private boolean isFieldsEmpty() {
        return emailField.getText().isBlank() || passwordField.getText().isBlank();
    }

    @FXML
    public void loginClicked() {
        if (!this.isFieldsEmpty()) {
            Platform.runLater(() -> {
                this.controller.loginPerson(emailField.getText(), passwordField.getText());
            });
        } else {
            errorLabel.setText(MessageError.EMPTY_FIELD.toString());
        }
    }

    @FXML
    public void signClicked() {
        this.view.switchScene("signup.fxml");
    }

    public void goForeward(final Person person) {
        this.getController().savePerson(person);
        this.getController().goToTheNextDataScene("home.fxml");
    }

    public void loginFailed() {
        errorLabel.setText(MessageError.INCORRECT_INPUT.toString());
    }

    public void userNotexist(){
        errorLabel.setText(MessageError.USER_NOT_EXIST.toString());
    }
}
