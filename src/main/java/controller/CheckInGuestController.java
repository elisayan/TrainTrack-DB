package controller;

import com.google.protobuf.Message;
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
        if (this.model.isPersonPresent(email)){
            this.view.showTickets(this.model.getPersonTicket(email));
        } else {
            this.view.showMessage(MessageError.PERSON_NOT_EXIST);
        }
    }
}
