package view.controller;

import controller.SignUpController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Person;

public class SignUpSceneController extends AbstractSceneController {
    @FXML
    private Button signButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField codeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Label errorLabel;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private BorderPane pane;

    private String typeClient;

    private SignUpController controller = new SignUpController(this);

    private boolean isFieldsEmpty() {
        if (passwordField.getText().isBlank()) {
            typeClient = "Guest";
        } else {
            typeClient = "User";
        }

        if (nameField.getText().isBlank() || surnameField.getText().isBlank() || codeField.getText().isBlank()
                || emailField.getText().isBlank() || addressField.getText().isBlank()) {
            return true;
        }

        return false;
    }

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    public void signUpClicked() {
        if (!this.isFieldsEmpty()) {
            Platform.runLater(() -> {
                final Person person = new Person();
                person.setName(nameField.getText());
                person.setSurname(surnameField.getText());
                person.setCf(codeField.getText());
                person.setEmail(emailField.getText());
                person.setPassword(passwordField.getText());
                person.setAddress(addressField.getText());
                person.setClientType(typeClient);
                person.setPersonType("Client");
                if (!phoneField.getText().isBlank()) {
                    person.setPhone(Integer.parseInt(phoneField.getText()));
                }
                this.controller.signUpPerson(person);
            });
        } else {
            errorLabel.setText(MessageError.EMPTY_FIELD.toString());
        }
    }

    public void goForeward(final Person person) {
        this.getController().savePerson(person);
        this.getController().goToTheNextDataScene("login.fxml");
    }

    public void singUpFailed() {
        errorLabel.setText(MessageError.ALREADY_EXIST.toString());
    }

}
