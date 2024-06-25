package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PurchaseHomeSceneController extends AbstractSceneController{

    @FXML
    private Label buyLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Button ticketsButton;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void subscriptionClicked() {
        this.view.switchScene("subscriptionPurchase.fxml");

    }

    @FXML
    private void ticketsClicked() {
        this.view.switchScene("ticketsPurchase.fxml");
    }

}
