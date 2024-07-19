package controller;

import db.CheckInTable;
import view.controller.CheckInGuestSceneController;
import view.controller.MessageError;

public class CheckInGuestController {

    private final CheckInGuestSceneController view;
    private final CheckInTable model;

    public CheckInGuestController(CheckInGuestSceneController view) {
        this.view = view;
        this.model = new CheckInTable();
    }

    public void checkPerson(String email){
        var name = this.model.getPersonName(email);
        if (name != null){
            this.view.showTickets(this.model.getPersonTicket(email), name);
        } else {
            this.view.showMessage(MessageError.PERSON_NOT_EXIST);
        }
    }
}
