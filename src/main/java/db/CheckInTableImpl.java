package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 
 */
public class CheckInTableImpl implements CheckInTable{
    private final DBConnection dataSource;
    private final String table;

    public CheckInTableImpl() {
        this.dataSource = new DBConnection();
        this.table = "checkin";
    }

    @Override
    public void getJourney() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJourney'");
    }

    @Override
    public void getPerson() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerson'");
    }

    @Override
    public void getService() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getService'");
    }

    @Override
    public void getSeat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSeat'");
    }

    
}
