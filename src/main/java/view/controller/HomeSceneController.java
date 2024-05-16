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

    @FXML
    public void homeClicked(){
        this.view.switchScene("home.fxml");
    }

    @FXML
    public void servicesClicked(){
        this.view.switchScene("services.fxml");
    }

    @FXML
    public void timetableClicked(){
        this.view.switchScene("timetable.fxml");
    }

    @FXML
    public void rankingClicked(){
        this.view.switchScene("ranking.fxml");
    }
}
