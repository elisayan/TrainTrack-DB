package db;

public class JourneyTable {
    private final DBConnection dataSource;
    private final String tableName;

    public JourneyTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Percorso";
    }

    public String getTopFiveDelayJourney(){
        return null;
    }
}
