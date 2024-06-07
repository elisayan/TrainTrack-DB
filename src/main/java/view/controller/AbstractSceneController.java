package view.controller;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import view.View;

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
    public void homeClicked(){
        this.view.switchScene("home.fxml");
    }

}
