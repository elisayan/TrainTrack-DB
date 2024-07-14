package controller;

import db.ThroughTable;
import view.controller.TicketsPurchaseSceneController;

import java.time.LocalDate;

public class PurchaseTicketController {
    private final TicketsPurchaseSceneController view;
    private final ThroughTable model;

    public PurchaseTicketController(TicketsPurchaseSceneController view) {
        this.view = view;
        this.model = new ThroughTable();
    }

    public void setAvailableTickets(String departure, String arrival, String typeTrain, LocalDate departureDate,
                                    String departureTime, boolean bikeSupplement, boolean petSupplement) {
        if (!this.model.availableTickets(departure, arrival, typeTrain, departureDate,
                departureTime, bikeSupplement, petSupplement).isEmpty()){
            this.view.showAvailableTickets();
        } else {
            this.view.showMessage();
        }
    }
}
