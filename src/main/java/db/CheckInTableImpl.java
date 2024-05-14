package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInTableImpl implements CheckInTableImpl {
    private final DBConnection dataSource;
    private final String table;

    public ActivityTable() {
        this.dataSource = new DBConnection();
        this.table = "checkin";
    }

    
}
