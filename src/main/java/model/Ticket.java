package model;

import java.time.LocalTime;
import java.time.LocalDate;

public class Ticket {
    private final String journeyID;
    private final String departureStation;
    private final String destinationStation;
    private final String typeTrain;
    private final LocalTime departureTime;
    private final float ticketPrice;
    private final LocalDate departureDate;

    public Ticket(String codPercorso, String nomeStazionePartenza,
                  String destinationStation, String typeTrain, LocalTime departureTime, float ticketPrice, LocalDate departureDate) {
        this.journeyID = codPercorso;
        this.departureStation = nomeStazionePartenza;
        this.destinationStation = destinationStation;
        this.typeTrain = typeTrain;
        this.departureTime = departureTime;
        this.ticketPrice = ticketPrice;
        this.departureDate = departureDate;
    }

    public String getJourneyID() {
        return journeyID;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getTypeTrain() {
        return typeTrain;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
}
