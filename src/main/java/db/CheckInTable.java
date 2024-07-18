package db;

import model.Ticket;

import java.util.List;

public class CheckInTable {

    private final DBConnection dataSource;
    private int serviceID;

    public CheckInTable() {
        this.dataSource = new DBConnection();
    }

    public boolean isPersonPresent(String email) {
        return false;
    }

    public List<Ticket> getPersonTicket(String email) {
        return null;
    }
}
