package db;

public class ThroughTable {
    private final DBConnection dataSource;
    private final String tableName;

    public ThroughTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Attraversato";
    }
}
