package model;

import java.util.TimeZone;

public class Journey {
    private int journeyID;
    private String email;
    private int trainID;
    private TimeZone travelTime;

    public int getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrainID() {
        return trainID;
    }

    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    public TimeZone getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(TimeZone travelTime) {
        this.travelTime = travelTime;
    }
}