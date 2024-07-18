package model;

import java.time.LocalDate;

public class Service {
    private int serviceID;
    private String departureStation;
    private String destinationStation;
    private String name;
    private String lastName;
    private String email;
    private LocalDate beginningDate;
    private String duration;
    private String type;
    private float price;
    private float km;
    private int journeyIDs;

    public Service (int serviceID, String departure, String destination, String name, String lastName, String email, 
                    LocalDate beginningDate, String duration, String type, float price, float km, int journeyIDs) {
        this.serviceID = serviceID;
        this.departureStation = departure;
        this.destinationStation = destination;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.beginningDate = beginningDate;
        this.duration = duration;
        this.type = type;
        this.price = price;
        this.km = km;
        this.journeyIDs = journeyIDs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJourneyIDs() {
        return journeyIDs;
    }

    public void setJourneyIDs(int journeyIDs) {
        this.journeyIDs = journeyIDs;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
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
        return "Service [ " + serviceID + departureStation + destinationStation + name + lastName + email + beginningDate + duration + type + price + km + journeyIDs + " ]" ;
    }
}
