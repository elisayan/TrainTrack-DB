package db;

import java.time.LocalTime;

public class ServiceTable {
    private final DBConnection dataSource;

    public ServiceTable() {
        this.dataSource = new DBConnection();
    }

    public void addTicket(String journeyID, String departureStation, String destinationStation, LocalTime departureTime,
                          String typeTrain, float ticketPrice) {

    }
}
