package db;

public class StationTable {
    private final DBConnection dataSource;
    private final String tableName;

    public StationTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Stazione";
    }
}
