package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class CheckInSceneController extends AbstractSceneController{
    @FXML
    private Label messageLabel;

    @FXML
    private TableView<?> ticketTable;

    @FXML
    void userClicked() {
        this.view.switchScene("login.fxml");
    }
}
