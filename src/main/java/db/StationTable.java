package db;

import java.util.*;

import model.Station;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StationTable {
    private final DBConnection dataSource;
    private final String tableName;

    public StationTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Stazione";
    }

    public Optional<Station> getStation(String stationID) {
        Station station = null;
        Optional<PreparedStatement> statement = Optional.empty();
        String query = "SELECT * " +
                "FROM " + tableName +
                "WHERE CodStazione=?";

        try (Connection connection = dataSource.getMySQLConnection()) {
            statement=Optional.of(connection.prepareStatement(query));
            statement.get().setString(1, stationID);
            final ResultSet resultSet = statement.get().executeQuery();

            if (resultSet.next()) {
                station = new Station();
                station.setStationID(resultSet.getString("CodStazione"));
                station.setStationName(resultSet.getString("Nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return Optional.ofNullable(station);
    }
}
