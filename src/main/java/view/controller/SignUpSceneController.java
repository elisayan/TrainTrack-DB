package view.controller;

import controller.SignUpController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Person;

public class SignUpSceneController extends AbstractSceneController {

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

    private String typeClient;

    private final SignUpController controller = new SignUpController(this);

    private boolean isFieldsEmpty() {
        typeClient = "utente";

        return nameField.getText().isBlank() || surnameField.getText().isBlank() || codeField.getText().isBlank()
                || emailField.getText().isBlank() || addressField.getText().isBlank() || passwordField.getText().isBlank();
    }

    @FXML
    public void userClicked() {
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
                person.setPersonType("cliente");
                if (!phoneField.getText().isBlank()) {
                    person.setPhone(Integer.parseInt(phoneField.getText()));
                }
                this.controller.signUpPerson(person);
            });
        } else {
            errorLabel.setText(Message.EMPTY_FIELD.toString());
        }
    }

    public void goForward(final Person person) {
        this.getController().setPerson(person);
        this.getController().goToTheNextDataScene("login.fxml");
    }

    public void singUpFailed() {
        errorLabel.setText(Message.ALREADY_EXIST.toString());
    }

}
