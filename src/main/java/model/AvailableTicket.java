package model;

import java.time.LocalTime;

public class AvailableTicket {
    private final String journeyID;
    private final String departureStation;
    private final String destinationStation;
    private final String typeTrain;

    private final LocalTime departureTime;
    //private final float ticketPrice;

    public AvailableTicket(String codPercorso, String nomeStazionePartenza,
                           String destinationStation, String typeTrain, LocalTime departureTime){//, float ticketPrice) {
        this.journeyID = codPercorso;
        this.departureStation = nomeStazionePartenza;
        this.destinationStation = destinationStation;
        this.typeTrain = typeTrain;
        this.departureTime = departureTime;
        //this.ticketPrice = ticketPrice;
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
//
//    public float getTicketPrice() {
//        return ticketPrice;
//    }
}
