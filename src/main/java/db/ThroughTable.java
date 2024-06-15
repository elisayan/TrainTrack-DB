package db;

import model.DelayInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ThroughTable {
    private final DBConnection dataSource;

    public ThroughTable() {
        this.dataSource = new DBConnection();
    }

    public List<DelayInfo> topFiveDelayJourney() {
        List<DelayInfo> delays = new ArrayList<>();
        try (Connection connection = dataSource.getMySQLConnection()) {
            Map<String, String> departureStations = getDepartureStations(connection);
            Map<String, String> destinationStations = getDestinationStations(connection);
            delays = getDelayInfos(connection, departureStations, destinationStations);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delays;
    }

    private Map<String, String> getDepartureStations(Connection connection) throws SQLException {
        String query = "SELECT a.CodPercorso, a.CodStazione AS StazionePartenza, s.nome " +
                "FROM Attraversato a, stazione s " +
                "WHERE s.CodStazione = a.CodStazione AND a.Ordine = '1'";

        Map<String, String> departureStations = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String codPercorso = rs.getString("CodPercorso");
                String nome = rs.getString("nome");
                departureStations.put(codPercorso, nome);
            }
        }
        return departureStations;
    }

    private Map<String, String> getDestinationStations(Connection connection) throws SQLException {
        String query = "SELECT a.CodPercorso, a.CodStazione AS StazioneDestinazione, s.nome " +
                "FROM Attraversato a, stazione s " +
                "WHERE s.CodStazione = a.CodStazione AND a.Ordine = (SELECT MAX(Ordine) FROM Attraversato WHERE CodPercorso = a.CodPercorso)";

        Map<String, String> destinationStations = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String codPercorso = rs.getString("CodPercorso");
                String nome = rs.getString("nome");
                destinationStations.put(codPercorso, nome);
            }
        }
        return destinationStations;
    }

    private List<DelayInfo> getDelayInfos(Connection connection, Map<String, String> departureStations,
            Map<String, String> destinationStations) throws SQLException {
        String query = "SELECT CodPercorso, AVG(TIMESTAMPDIFF(MINUTE, OrarioArrivoPrevisto, OrarioArrivoReale)) AS MediaMinutiRitardo "
                +
                "FROM Attraversato " +
                "WHERE StatoArrivo = 'ritardo' " +
                "GROUP BY CodPercorso " +
                "ORDER BY MediaMinutiRitardo DESC " +
                "LIMIT 5";

        List<DelayInfo> delays = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String codPercorso = rs.getString("CodPercorso");
                float mediaMinutiRitardo = rs.getFloat("MediaMinutiRitardo");
                String stazionePartenzaNome = departureStations.get(codPercorso);
                String stazioneDestinazioneNome = destinationStations.get(codPercorso);

                delays.add(
                        new DelayInfo(codPercorso, stazionePartenzaNome, stazioneDestinazioneNome, mediaMinutiRitardo));
            }
        }
        return delays;
    }
}
