package db;

import java.util.*;

import model.DelayInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThroughTable {
    private final DBConnection dataSource;
    private final String tableName;

    public ThroughTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Attraversato";
    }

    // get JourneyID, departureStation, destinationStation, calculate delay and
    // early average

    public List<DelayInfo> topFiveDelayJourney() {
        final Connection connection = dataSource.getMySQLConnection();

        Optional<PreparedStatement> statement = Optional.empty();
        final String query = "SELECT CodPercorso, AVG(TIMESTAMPDIFF(MINUTE, OrarioArrivoPrevisto, OrarioArrivoReale)) AS MediaMinutiRitardo " +
                             "FROM " + tableName + " " +
                             "WHERE StatoArrivo='ritardo' " +
                             "GROUP BY CodPercorso " +
                             "ORDER BY MediaMinutiRitardo DESC " +
                             "LIMIT 5";

        List<DelayInfo> topFiveDelays = new LinkedList<>();

        try {
            statement = Optional.of(connection.prepareStatement(query));
            final ResultSet resultset = statement.get().executeQuery();

            while (resultset.next()) {
                String codPercorso = resultset.getString("CodPercorso");
                float mediaMinutiRitardo = resultset.getFloat("MediaMinutiRitardo");
                topFiveDelays.add(new DelayInfo(codPercorso, mediaMinutiRitardo));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!statement.isEmpty()) {
                    statement.get().close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return topFiveDelays;
    }
}
