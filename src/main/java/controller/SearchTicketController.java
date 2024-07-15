package controller;

import db.ServiceTable;
import db.ThroughTable;
import model.AvailableTicket;
import view.controller.SearchResultSceneController;

public class SearchTicketController {

    private final SearchResultSceneController view;
    private final ServiceTable model;

    public SearchTicketController(SearchResultSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void selectedTicket(AvailableTicket newValue) {
        this.model.addTicket(newValue.getJourneyID(), newValue.getDepartureStation(), newValue.getDestinationStation(),
                newValue.getDepartureTime(), newValue.getTypeTrain(), newValue.getTicketPrice());
        
    }

    //passare le info selezionate dalla tabella alla prossima scena di acquisto
}
