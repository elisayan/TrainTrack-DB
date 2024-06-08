package view.controller;

import controller.Controller;
import javafx.scene.Parent;
import view.View;

public interface SceneController {
    void initialize(View view, Controller controller);

    void setRoot(Parent root);

    Parent getRoot();
}