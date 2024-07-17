package view.controller;

import javafx.fxml.FXML;

public class PurchaseHomeSceneController extends AbstractSceneController{

    @FXML
    private void userClicked() {
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
