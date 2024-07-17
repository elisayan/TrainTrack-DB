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
    private void purchaseClicked(){this.view.switchScene("purchaseHome.fxml");}

    @FXML
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        if (controller.getCurrentPerson() != null) {
            welcomeLabel.setText("Hello " + controller.getCurrentPerson().getName() + "!");
        }
    }
}