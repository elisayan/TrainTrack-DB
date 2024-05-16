package view.controller;

import java.util.Optional;

import controller.Controller;
import javafx.scene.Parent;
import model.Person;
import view.View;

public interface SceneController {
    void initialize(View view, Controller controller);

    void setRoot(Parent root);

    Parent getRoot();

    void init(Optional<Person> person);
}