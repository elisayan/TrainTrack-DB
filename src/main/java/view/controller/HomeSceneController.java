package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeSceneController extends AbstractSceneController{
    @FXML
    private Button login;

    @FXML
    public void loginClicked(){
        this.view.switchScene("login.fxml");
    }
}
