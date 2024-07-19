package controller;

import java.time.LocalDate;
import java.util.List;

import db.ServiceTable;
import model.Subscription;
import view.controller.Message;
import view.controller.SubscriptionPurchaseSceneController;

public class SubscriptionPurchaseController {
     
    private final SubscriptionPurchaseSceneController view;
    private final ServiceTable model;

    public SubscriptionPurchaseController(SubscriptionPurchaseSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void setListOfSubscriptions(String departure, String destination, LocalDate beginningDate, String duration) {
        if (departure == null || departure.trim().isEmpty() ||
            destination == null || destination.trim().isEmpty() ||
            beginningDate == null || duration == null || duration.trim().isEmpty()) {
            this.view.showError(Message.EMPTY_FIELD.toString());
            return;
        }

        List<List<Subscription>> subscriptionGroups = this.model.subscriptionsSearched(departure, destination, beginningDate, duration);
        if (!subscriptionGroups.isEmpty()) {
            this.view.showSubscriptionsSearched(subscriptionGroups);
        } else {
            this.view.showError(Message.SUBSCRIPTION_NOT_EXIST.toString());
        }
    }
}
