package controller;

import db.ServiceTable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Subscription;
import view.controller.SubscriptionDataSceneController;

public class SubscriptionDataController {

    private final SubscriptionDataSceneController view;
    private final ServiceTable model;
    
    @FXML
    private Label subscriptionDetailsLabel;

    public SubscriptionDataController(SubscriptionDataSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void setSubscriptionData(Subscription subscription) {
        // Display the subscription details or process as needed
        subscriptionDetailsLabel.setText(subscription.toString());
    }
}
