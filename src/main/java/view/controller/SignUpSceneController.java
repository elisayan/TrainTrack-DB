package view.controller;

import controller.SignUpController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
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
    private Label errorLabel;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private AnchorPane pane;

    private SignUpController controller;

    private final EventHandler<MouseEvent> signUp = (l)->{
        if (!this.checkEmptyFields()) {
            Platform.runLater(()->{
                final Person person = new Person();
                person.setAddress(addressText.getText());
                person.setCf(codeText.getText());
                person.setEmail(emailText.getText());
                person.setPassword(passwordText.getText());
                person.setPhone(Integer.parseInt(phoneText.getText()));
                person.setSurname(surnameText.getText());
                this.controller.signUpPerson(person);
            });
        } else{
            errorLabel.setText(MessageError.ERROR.toString());
        }
    };

    private boolean checkEmptyFields(){
        return false;
    }

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    public void signUpClicked() {
        this.view.switchScene("home.fxml");
    }

    public void goForeward(final Person person) {
        this.getController().savePerson(person);
        this.signUpClicked();
        //this.getController().goToTheNextDataScene("home.fxml");
    }

    public void singUpFailed(){
        
    }

}
