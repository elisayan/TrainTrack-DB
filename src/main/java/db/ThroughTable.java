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
        List<DelayInfo> delayInfos = new LinkedList<>();
        try (Connection connection = dataSource.getMySQLConnection()) {
            Map<String, String> departureStations = getDepartureStations(connection);
            Map<String, String> destinationStations = getArrivalStations(connection);
            Map<String, String> stationNames = getStationNames(connection);
            List<DelayInfo> delays = getDelays(connection);

            for (DelayInfo delay : delays) {
                String departureCodice = departureStations.get(delay.getCodPercorso());
                String destinationCodice = destinationStations.get(delay.getCodPercorso());
                String departureNome = stationNames.get(departureCodice);
                String destinationNome = stationNames.get(destinationCodice);
                
                delay.setStazionePartenzaCodice(departureCodice);
                delay.setStazionePartenzaNome(departureNome);
                delay.setStazioneDestinazioneCodice(destinationCodice);
                delay.setStazioneDestinazioneNome(destinationNome);

                delayInfos.add(new DelayInfo(delay.getCodPercorso(), departureCodice, departureNome, destinationCodice, destinationNome, delay.getMediaMinutiRitardo()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delayInfos;
    }

    private Map<String, String> getDepartureStations(Connection connection) {
        Map<String, String> departureStations = new HashMap<>();
        String query = "SELECT CodPercorso, CodStazione AS StazionePartenza " +
                "FROM " + tableName + " " +
                "WHERE Ordine = '1'";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String codPercorso = resultSet.getString("CodPercorso");
                String stazionePartenza = resultSet.getString("StazionePartenza");
                departureStations.put(codPercorso, stazionePartenza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departureStations;
    }

    private Map<String, String> getArrivalStations(Connection connection) {
        Map<String, String> destinationStations = new HashMap<>();
        String query = "SELECT CodPercorso, CodStazione AS StazioneDestinazione " +
                "FROM " + tableName + " " +
                "WHERE Ordine = (SELECT MAX(Ordine) FROM " + tableName + " WHERE CodPercorso = " + tableName
                + ".CodPercorso)";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                String codPercorso = resultSet.getString("CodPercorso");
                String stazioneDestinazione = resultSet.getString("StazioneDestinazione");
                destinationStations.put(codPercorso, stazioneDestinazione);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinationStations;
    }

    private Map<String, String> getStationNames(Connection connection) throws SQLException {
        Map<String, String> stationNames = new HashMap<>();
        String query = "SELECT CodStazione, Nome FROM Stazione";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String codStazione = resultSet.getString("CodStazione");
                String nome = resultSet.getString("Nome");
                stationNames.put(codStazione, nome);
            }
        }
        return stationNames;
    }
    
    private List<DelayInfo> getDelays(Connection connection) throws SQLException {
        List<DelayInfo> delays = new LinkedList<>();
        String query = "SELECT CodPercorso, AVG(TIMESTAMPDIFF(MINUTE, OrarioArrivoPrevisto, OrarioArrivoReale)) AS MediaMinutiRitardo " +
                       "FROM " + tableName + " " +
                       "WHERE StatoArrivo = 'ritardo' " +
                       "GROUP BY CodPercorso " +
                       "ORDER BY MediaMinutiRitardo DESC " +
                       "LIMIT 5";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String codPercorso = resultSet.getString("CodPercorso");
                float mediaMinutiRitardo = resultSet.getFloat("MediaMinutiRitardo");
                delays.add(new DelayInfo(codPercorso, null, null, null, null, mediaMinutiRitardo));
            }
        }
        return delays;
    }  
}
