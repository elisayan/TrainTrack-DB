package controller;

import java.io.IOException;

import db.ServiceTable;
import model.Service;
import view.controller.SubscriptionDataSceneController;

public class SubscriptionDataController {

    private final SubscriptionDataSceneController view;
    private final ServiceTable model;
    private Service subscription;


    public SubscriptionDataController(SubscriptionDataSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }


    public void notifyView() throws IOException {
        this.view.bookedSuccessful();
    }
}
