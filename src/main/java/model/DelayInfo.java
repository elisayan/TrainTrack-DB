package model;

public class DelayInfo {
    private String codPercorso;
    private float mediaMinutiRitardo;
    private String departureStation;
    private String arrivalStation;

    public DelayInfo(String departureStation, String arrivalStation) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }

    public DelayInfo(String codPercorso, float mediaMinutiRitardo) {
        this.codPercorso = codPercorso;
        this.mediaMinutiRitardo = mediaMinutiRitardo;
    }

    public String getCodPercorso() {
        return codPercorso;
    }

    public float getMediaMinutiRitardo() {
        return mediaMinutiRitardo;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }
}
