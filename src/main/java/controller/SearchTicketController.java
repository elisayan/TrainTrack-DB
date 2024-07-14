package controller;

import db.ThroughTable;
import view.controller.SearchSceneController;

public class SearchTicketController {

    private final SearchSceneController view;
    private final ThroughTable model;

    public SearchTicketController(SearchSceneController view) {
        this.view = view;
        this.model = new ThroughTable();
    }

    //passare le info selezionate dalla tabella alla prossima scena di acquisto
}
