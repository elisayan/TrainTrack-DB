package model;

import java.util.TimeZone;

public class Journey {
    private int journeyID;
    private String cf;
    private int trainID;
    private TimeZone travelTime;

    public int getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
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