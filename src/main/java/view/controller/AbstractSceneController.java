package view.controller;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import view.View;

import java.time.LocalTime;

public abstract class AbstractSceneController implements SceneController {
    protected View view;
    protected Controller controller;
    protected Parent root;

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;
    }

    protected Controller getController() {
        return controller;
    }

    @Override
    public void initialize(View view, Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    @FXML
    public void homeClicked() {
        this.view.switchScene("home.fxml");
    }

    public void initializeTime(ChoiceBox<String> timeChoice) {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;
        if (roundedMinute == 0) {
            hour++;
        }

        timeChoice.getItems().clear();
        for (int h = hour; h <= 23; h++) {
            for (int m = (h == hour) ? roundedMinute : 0; m < 60; m += 30) {
                timeChoice.getItems().add(String.format("%02d:%02d", h, m));
            }
        }
    }

}
