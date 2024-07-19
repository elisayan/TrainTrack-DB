package controller;

import db.ServiceTable;
import model.Ticket;
import view.controller.MessageError;
import view.controller.PassengerDetailSceneController;

import java.io.IOException;

public class PassengerDetailController {
    private final PassengerDetailSceneController view;
    private final ServiceTable model;

    public PassengerDetailController(PassengerDetailSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void checkEmail(Ticket ticket, String firstName, String lastName,
                           String email, String address, String cf, String voucher) throws IOException {
        this.model.saveOrUpdateGuest(email, firstName, lastName, address, cf);

        if (this.model.haveVoucher(voucher, email)) {
            addTicketAndNotifyView(ticket, firstName, lastName, email, voucher);
        } else {
            this.view.showMessage(MessageError.VOUCHER_NOT_VALID);
        }
    }

    public void successful(Ticket ticket, String firstName, String lastName,
                           String email, String address, String cf, String voucher) throws IOException {
        this.model.saveOrUpdateGuest(email, firstName, lastName, address, cf);
        addTicketAndNotifyView(ticket, firstName, lastName, email, voucher);
    }

    private void addTicketAndNotifyView(Ticket ticket, String firstName, String lastName,
                                        String email, String voucher) throws IOException {
        float voucherValue = 0.0F;
        if (voucher != null) {
            voucherValue = this.model.getVoucherValue(voucher);
        }
        var updatePrice = this.model.addTicket(ticket, firstName, lastName, email, voucherValue);
        this.model.decreaseTotalSeats(ticket.getJourneyID());
        this.model.updateTotalPurchase(email, updatePrice);
        if (voucher != null) {
            this.model.useVoucher(voucher);
        }
        this.view.bookedSuccessful();
    }
}
