package view.controller;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.View;

public class HomeSceneController extends AbstractSceneController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML

    private void rankingClicked(){
        this.view.switchScene("rankingHome.fxml");
    }
  
    @FXML
    public void timetableClicked() {
        this.view.switchScene("timetable.fxml");
    }

    @FXML
    private void purchaseClicked(){this.view.switchScene("purchaseHome.fxml");}

    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        //System.out.println("HomeSceneController initialized: "+controller.getCurrentPerson().getEmail());
        System.out.println("initialized home scene controller " + controller);
        if (controller.getCurrentPerson().isPresent()) {
            welcomeLabel.setText("Hello " + controller.getCurrentPerson().get().getName() + "!");
        }
    }
}