package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Person;

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

    @FXML
    public void loginClicked() {
        this.view.switchScene("home.fxml");
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
    }
}
