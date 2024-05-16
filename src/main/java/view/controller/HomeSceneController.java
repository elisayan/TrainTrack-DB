package view.controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Person;

public class HomeSceneController extends AbstractSceneController{
    @FXML
    private Button login;

    @FXML
    public void loginClicked(){
        this.view.switchScene("login.fxml");
    }

    @Override
    public void init(Optional<Person> person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }
}
