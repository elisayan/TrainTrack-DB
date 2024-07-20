package view.controller;

import javafx.stage.Stage;

public class ConfirmationWindowSceneController extends AbstractSceneController{
    
    @Override
    public void homeClicked() {
        super.homeClicked();
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.close();
    }

}
