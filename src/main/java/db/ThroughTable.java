package db;

import model.DelayInfo;
import model.EarlyInfo;
import model.JourneyInfo;

import view.controller.*;

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

    public List<EarlyInfo> topFiveEarlyJourney() {
        List<EarlyInfo> early = new ArrayList<>();
        try (Connection connection = dataSource.getMySQLConnection()) {
            Map<String, String> departureStations = getDepartureStations(connection);
            Map<String, String> destinationStations = getDestinationStations(connection);
            early = getEarlyInfos(connection, departureStations, destinationStations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return early;
    }

    public List<JourneyInfo> journeyInfo(String station) {
        List<JourneyInfo> info = new ArrayList<>();
            try (Connection connection = dataSource.getMySQLConnection()) {
            List<JourneyInfo> journeyInfos = getJourneyInfos(connection, station);
            info = journeyInfos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
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
                "WHERE TIMESTAMPDIFF(MINUTE, OrarioArrivoPrevisto, OrarioArrivoReale) >= 5 " +
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

    private List<EarlyInfo> getEarlyInfos(Connection connection, Map<String, String> departureStations,
            Map<String, String> destinationStations) throws SQLException {
        String query = "SELECT CodPercorso, AVG(TIMESTAMPDIFF(MINUTE, OrarioArrivoReale, OrarioArrivoPrevisto)) AS MediaMinutiAnticipo "
                +
                "FROM Attraversato " +
                "WHERE TIMESTAMPDIFF(MINUTE, OrarioArrivoReale, OrarioArrivoPrevisto) > 5 " +
                "GROUP BY CodPercorso " +
                "ORDER BY MediaMinutiAnticipo DESC " +
                "LIMIT 5";
        List<EarlyInfo> early = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String codPercorso = rs.getString("CodPercorso");
                float mediaMinutiAnticipo = rs.getFloat("MediaMinutiAnticipo");
                String stazionePartenzaNome = departureStations.get(codPercorso);
                String stazioneDestinazioneNome = destinationStations.get(codPercorso);

                early.add(
                        new EarlyInfo(codPercorso, stazionePartenzaNome, stazioneDestinazioneNome,
                                mediaMinutiAnticipo));
            }
        }
        return early;
    }

    public static List<JourneyInfo> getJourneyInfos(Connection connection, String station) throws SQLException {
        String selectPercorsoQuery = "SELECT a.CodPercorso, a.Binario, a.OrarioPartenzaPrevisto, " +
                                     "CASE " +
                                     "WHEN TIMESTAMPDIFF(MINUTE, a.OrarioArrivoPrevisto, a.OrarioArrivoReale) > 5 THEN 'delayed' " +
                                     "WHEN TIMESTAMPDIFF(MINUTE, a.OrarioArrivoReale, a.OrarioArrivoPrevisto) > 5 THEN 'early' " +
                                     "ELSE 'on time' " +
                                     "END AS StatoArrivo, " +
                                     "(SELECT s.Nome FROM stazione s JOIN attraversato a2 ON s.CodStazione = a2.CodStazione " +
                                     "WHERE a2.Ordine = '1' AND a2.CodPercorso = a.CodPercorso LIMIT 1) AS StazionePartenza, " +
                                     "(SELECT s.Nome FROM stazione s JOIN attraversato a3 ON s.CodStazione = a3.CodStazione " +
                                     "WHERE a3.CodPercorso = a.CodPercorso ORDER BY a3.Ordine DESC LIMIT 1) AS StazioneArrivo " +
                                     "FROM attraversato a " +
                                     "JOIN stazione s ON s.CodStazione = a.CodStazione " +
                                     "WHERE s.Nome = ?";


        List<JourneyInfo> journeyInfos = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(selectPercorsoQuery)) {
            pstmt.setString(1, station);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String codPercorso = rs.getString("CodPercorso");
                    int binario = rs.getInt("Binario");
                    String orarioPartenzaPrevisto = rs.getString("OrarioPartenzaPrevisto");
                    String statoArrivo = rs.getString("StatoArrivo");
                    String stazionePartenza = rs.getString("StazionePartenza");
                    String stazioneArrivo = rs.getString("StazioneArrivo");

                    JourneyInfo journeyInfo = new JourneyInfo(codPercorso, stazionePartenza, stazioneArrivo, orarioPartenzaPrevisto, binario, statoArrivo);
                    journeyInfos.add(journeyInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return journeyInfos;
    }
}
