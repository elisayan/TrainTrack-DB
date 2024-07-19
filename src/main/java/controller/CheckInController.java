package controller;

import db.CheckInTable;
import model.Ticket;
import view.controller.CheckInSceneController;
import view.controller.MessageError;

import java.util.List;

public class CheckInController {

    private final CheckInSceneController view;
    private final CheckInTable model;

    public CheckInController(CheckInSceneController view) {
        this.view = view;
        this.model = new CheckInTable();
    }

    public void getPersonTickets(String email) {
        this.view.showName(this.model.getPersonName(email));
        this.view.fillTicketTable(this.model.getPersonTicket(email));
    }

    public void initializedCheckIn(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (!this.model.isCheckInExist(ticket.getCodServizio(), ticket.getEmail())) {
                this.view.showCheckedTickets(ticket);
            } else {
                ticket.setCheckedIn(true);
                ticket.setDisabled(true);
            }
        }
    }

    public void updateCheckInStatus(Ticket ticket, boolean checkedIn) {
        if (!this.model.isCheckInExist(ticket.getCodServizio(), ticket.getEmail())) {
            if (checkedIn) {
                if (this.model.isCheckInValid(ticket.getCodServizio())) {
                    model.insertCheckIn(ticket.getEmail(), ticket.getCodServizio());
                    this.view.showMessage(MessageError.CHECKIN_SUCCESSFUL);
                    ticket.setCheckedIn(true);
                    //ticket.setDisabled(true);
                } else {
                    ticket.setCheckedIn(false);
                    this.view.showMessage(MessageError.CHECKIN_NOT_IN_TIME);
                }
            }
        }else {
            ticket.setCheckedIn(true);
            ticket.setDisabled(true);
            this.view.showMessage(MessageError.CHECKIN_ALREADY_DONE);
        }
    }
}
