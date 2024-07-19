package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {
    private final String journeyID;
    private final String departureStation;
    private final String destinationStation;
    private final String typeTrain;
    private final LocalTime departureTime;
    private final float ticketPrice;
    private final LocalDate departureDate;
    private final BooleanProperty checkedIn;
    private String email;
    private int codServizio;

    public Ticket(String codPercorso, String nomeStazionePartenza, String destinationStation, String typeTrain,
                  LocalTime departureTime, float ticketPrice, LocalDate departureDate) {
        this.journeyID = codPercorso;
        this.departureStation = nomeStazionePartenza;
        this.destinationStation = destinationStation;
        this.typeTrain = typeTrain;
        this.departureTime = departureTime;
        this.ticketPrice = ticketPrice;
        this.departureDate = departureDate;
        this.checkedIn = new SimpleBooleanProperty(false);
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

    public boolean isCheckedIn() {
        return checkedIn.get();
    }

    public BooleanProperty checkedInProperty() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn.set(checkedIn);
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCodServizio(int codServizio) {
        this.codServizio = codServizio;
    }

    public int getCodServizio() {
        return codServizio;
    }

    public void setDisabled(boolean disabled) {
        this.checkedIn.set(disabled);
    }
}
