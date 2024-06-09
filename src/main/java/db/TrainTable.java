package db;

public class TrainTable {
    private final DBConnection dataSource;
    private final String tableName;

    public TrainTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Treno";
    }
}
