package model;

import java.time.LocalDate;

public class Subscription {
    private String departureStation;
    private String destinationStation;
    private LocalDate beginningDate;
    private String duration;
    private String type;
    private float price;
    private float km;
    private String journeyID;

    public Subscription(String departure, String destination, LocalDate beginningDate, String duration, String type, float price, float km, String journeyID) {
        this.departureStation = departure;
        this.destinationStation = destination;
        this.beginningDate = beginningDate;
        this.duration = duration;
        this.type = type;
        this.price = price;
        this.km = km;
        this.journeyID = journeyID;
    }

    
    public String getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(String journeyID) {
        this.journeyID = journeyID;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }
    
    @Override
    public String toString() {
        return "Subscription [ " + departureStation + destinationStation + beginningDate + duration + type + price + km + " ]" ;
    }
}
