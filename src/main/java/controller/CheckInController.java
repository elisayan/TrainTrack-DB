package controller;

import db.CheckInTable;
import view.controller.CheckInSceneController;

public class CheckInController {

    private final CheckInSceneController view;
    private final CheckInTable model;

    public CheckInController(CheckInSceneController view) {
        this.view = view;
        this.model = new CheckInTable();
    }

    public void getPersonTickets(String email){
        this.view.showName(this.model.getPersonName(email));
        this.view.fillTicketTable(this.model.getPersonTicket(email));
    }
}
