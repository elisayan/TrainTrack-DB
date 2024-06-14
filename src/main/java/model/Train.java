package model;

public class Train {
    private String trainID;
    private int totalSeats;
    private String type;
    private String trainClass;

    public String getTrainID() {
        return trainID;
    }
    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }
    public int getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTrainClass() {
        return trainClass;
    }
    public void setTrainClass(String trainClass) {
        this.trainClass = trainClass;
    }
}
