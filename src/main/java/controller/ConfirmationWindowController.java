package controller;

import db.ServiceTable;
import model.Service;
import model.Subscription;
import view.controller.ConfirmationWindowSceneController;

public class ConfirmationWindowController {
    
    private final ConfirmationWindowSceneController view;
    private final ServiceTable model;
    private Service subscription;

    public ConfirmationWindowController(ConfirmationWindowSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

}

