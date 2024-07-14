package db;

import model.DelayInfo;
import model.EarlyInfo;
import model.AvailableTicket;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
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

    private Map<String, String> getDepartureStations(Connection connection) throws SQLException {
        String query = "SELECT a.CodPercorso, a.CodStazione AS StazionePartenza, s.nome " +
                "FROM Attraversato a, stazione s " +
                "WHERE s.CodStazione = a.CodStazione AND a.Ordine = '1'";

        return getStation(connection, query);
    }

    private Map<String, String> getDestinationStations(Connection connection) throws SQLException {
        String query = "SELECT a.CodPercorso, a.CodStazione AS StazioneDestinazione, s.nome " +
                "FROM Attraversato a, stazione s " +
                "WHERE s.CodStazione = a.CodStazione AND a.Ordine = (SELECT MAX(Ordine) FROM Attraversato WHERE CodPercorso = a.CodPercorso)";

        return getStation(connection, query);
    }

    private Map<String, String> getStation(Connection connection, String query) throws SQLException {
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
                + "FROM Attraversato "
                + "WHERE TIMESTAMPDIFF(MINUTE, OrarioArrivoPrevisto, OrarioArrivoReale) >= 5 "
                + "GROUP BY CodPercorso "
                + "ORDER BY MediaMinutiRitardo DESC "
                + "LIMIT 5";

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
                + "FROM Attraversato "
                + "WHERE TIMESTAMPDIFF(MINUTE, OrarioArrivoReale, OrarioArrivoPrevisto) > 5 "
                + "GROUP BY CodPercorso "
                + "ORDER BY MediaMinutiAnticipo DESC "
                + "LIMIT 5";
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

    public List<AvailableTicket> availableTickets(String departure, String arrival, String typeTrain, LocalDate departureDate,
                                                  String departureTime, boolean bikeSupplement, boolean petSupplement) {
        String query = "SELECT " +
                "    p.CodPercorso, " +
                "    sp.Nome AS NomeStazionePartenza, " +
                "    sa.Nome AS NomeStazioneArrivo, " +
                "    a1.Data, " +
                "    a1.OrarioPartenzaPrevisto, " +  // Aggiunta una virgola qui
                "    t.Tipo " +
                "FROM " +
                "    Attraversato a1 " +
                "    JOIN Attraversato a2 ON a1.CodPercorso = a2.CodPercorso " +
                "    JOIN Stazione sp ON a1.CodStazione = sp.CodStazione " +
                "    JOIN Stazione sa ON a2.CodStazione = sa.CodStazione " +
                "    JOIN Percorso p ON a1.CodPercorso = p.CodPercorso " +
                "    JOIN Treno t ON p.CodTreno = t.CodTreno " +
                "WHERE " +
                "    sp.Nome = ? " +
                "    AND sa.Nome = ? " +
                "    AND a1.Data = ? " +
                "    AND (a1.OrarioPartenzaPrevisto >= ? OR a1.OrarioPartenzaReale >= ?) " +
                "    AND t.Tipo = ? " +
                "    AND a1.Ordine < a2.Ordine " +
                "ORDER BY " +
                "    a1.OrarioPartenzaPrevisto";



        List<AvailableTicket> availableTickets = new ArrayList<>();

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, departure);
            stmt.setString(2, arrival);
            stmt.setDate(3, Date.valueOf(departureDate));
            stmt.setString(4, departureTime);
            stmt.setString(5, departureTime);
            stmt.setString(6, typeTrain);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AvailableTicket availableTicketRequest = new AvailableTicket(
                            rs.getString("CodPercorso"),
                            rs.getString("NomeStazionePartenza"),
                            rs.getString("NomeStazioneArrivo"),
                            rs.getString("Tipo"),
                            rs.getTime("OrarioPartenzaPrevisto").toLocalTime()
                           // rs.getFloat("Prezzo")
                    );
                    availableTickets.add(availableTicketRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableTickets;
    }
}
