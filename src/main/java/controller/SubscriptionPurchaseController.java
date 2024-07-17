package controller;

import java.time.LocalDate;
import java.util.List;

import db.ServiceTable;
import model.Subscription;
import view.controller.MessageError;
import view.controller.SubscriptionPurchaseSceneController;

public class SubscriptionPurchaseController {
     
    private final SubscriptionPurchaseSceneController view;
    private final ServiceTable model;

    public SubscriptionPurchaseController(SubscriptionPurchaseSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void setSubscriptions(String departure, String destination, LocalDate beginningDate, String duration) {
        if (departure == null || departure.trim().isEmpty() ||
            destination == null || destination.trim().isEmpty() ||
            beginningDate == null || duration == null || duration.trim().isEmpty()) {
            this.view.showError(MessageError.EMPTY_FIELD.toString());
            return;
        }
    
        List<Subscription> subscriptionSearched = this.model.subscriptionSearched(departure, destination, beginningDate, duration);
        if (!subscriptionSearched.isEmpty()) {
            this.view.showSubscriptionSearched(subscriptionSearched);
        } else {
            this.view.showError(MessageError.ERROR.toString());
        }
    }
    
}
