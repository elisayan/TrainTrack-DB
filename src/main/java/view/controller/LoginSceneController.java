package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class LoginSceneController extends AbstractSceneController {
    
    private Button login;

    public void loginClicked(){
        this.view.switchScene("home.fxml");
    }
}
