package view.controller;

import javafx.stage.Stage;
import model.Service;

public class ConfirmationWindowSceneController extends AbstractSceneController{
    
    @Override
    public void homeClicked() {
        super.homeClicked();
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.close();
    }

}
