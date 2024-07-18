package view.controller;

import controller.Controller;
import javafx.fxml.FXML;
import view.View;

public class PurchaseHomeSceneController extends AbstractSceneController {

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
    }

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

    @FXML
    private void checkInClicked() {
        if (getController().getCurrentPerson().isPresent()) {
            this.view.switchScene("checkIn.fxml");
        } else {
            this.view.switchScene("checkInGuest.fxml");
        }
    }

}
