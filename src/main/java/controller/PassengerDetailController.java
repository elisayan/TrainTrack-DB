package controller;

import db.ServiceTable;
import view.controller.MessageError;
import view.controller.PassengerDetailSceneController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class PassengerDetailController {
    private final PassengerDetailSceneController view;
    private final ServiceTable model;

    public PassengerDetailController(PassengerDetailSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void checkVoucher(String voucher, String email) {
        if (this.model.haveVoucher(voucher, email)) {
            this.model.useVoucher(voucher);
        } else {
            this.view.showMessage(MessageError.VOUCHER_NOT_VALID);
        }
    }

    public void checkEmail(String journeyID, String departureStation, String destinationStation, LocalDate departureDate,
                           LocalTime departureTime, String typeTrain, float ticketPrice, String firstName, String lastName,
                           String email, String address, String cf) throws IOException {
        if (this.model.isGuest(email)) {
            this.model.saveOrUpdateGuest(email, firstName, lastName, address, cf);
        } else {
            this.model.updateTotalPurchase(email, ticketPrice);
        }
        this.model.addTicket(journeyID, departureStation, destinationStation, departureDate, departureTime,
                typeTrain, ticketPrice, firstName, lastName, email);
        this.view.bookedSuccessful();
    }
}
