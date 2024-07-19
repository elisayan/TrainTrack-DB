package db;

import model.DelayInfo;
import model.EarlyInfo;
import model.JourneyInfo;
import model.Station;
import model.Ticket;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public List<JourneyInfo> journeyInfo(Station station) {
        List<JourneyInfo> info = new ArrayList<>();
        try (Connection connection = dataSource.getMySQLConnection()) {
            info = getJourneyInfos(connection, station);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
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
                + "WHERE TIMESTAMPDIFF(MINUTE, OrarioArrivoReale, OrarioArrivoPrevisto) >= 5 "
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

    public List<Ticket> availableTickets(String departure, String arrival, String typeTrain, LocalDate departureDate,
                                         String departureTime, int supplement) {
        String query = "SELECT " +
                "    p.CodPercorso, " +
                "    sp.Nome AS NomeStazionePartenza, " +
                "    sa.Nome AS NomeStazioneArrivo, " +
                "    a1.Data, " +
                "    a1.OrarioPartenzaPrevisto, " +
                "    t.Tipo, " +
                "    p.Prezzo, " +
                "    (SELECT MAX(a.Ordine) FROM Attraversato a WHERE a.CodPercorso = a1.CodPercorso) AS MaxOrdine, " +
                "    a2.Ordine - a1.Ordine AS NumeroStazioni " +
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
                "    AND t.PostiTotali > 0 " +
                "ORDER BY " +
                "    a1.OrarioPartenzaPrevisto";

        List<Ticket> tickets = new ArrayList<>();

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
                    String codPercorso = rs.getString("CodPercorso");
                    String nomeStazionePartenza = rs.getString("NomeStazionePartenza");
                    String nomeStazioneArrivo = rs.getString("NomeStazioneArrivo");
                    LocalDate dataPartenza = rs.getDate("Data").toLocalDate();
                    LocalTime orarioPartenzaPrevisto = rs.getTime("OrarioPartenzaPrevisto").toLocalTime();
                    String tipo = rs.getString("Tipo");
                    float prezzo = rs.getFloat("Prezzo");
                    int maxOrdine = rs.getInt("MaxOrdine");
                    int numeroStazioni = rs.getInt("NumeroStazioni");

                    float prezzoFinale = Math.round((((prezzo / maxOrdine) * numeroStazioni) + supplement) * 100) / 100f;

                    Ticket ticketRequest = new Ticket(
                            codPercorso,
                            nomeStazionePartenza,
                            nomeStazioneArrivo,
                            tipo,
                            orarioPartenzaPrevisto,
                            prezzoFinale,
                            dataPartenza
                    );
                    tickets.add(ticketRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static List<JourneyInfo> getJourneyInfos(Connection connection, Station station) throws SQLException {
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
                "WHERE s.Nome = ? " +
                "AND TIMESTAMP(a.Data, a.OrarioPartenzaReale) > NOW() " +
                "ORDER BY TIMESTAMP(a.Data, a.OrarioPartenzaReale) " +
                "LIMIT 8";

        List<JourneyInfo> journeyInfos = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(selectPercorsoQuery)) {
            pstmt.setString(1, station.getStationName());

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

    public boolean isStationExist(Station station) throws SQLException {
        String query = "SELECT COUNT(*) FROM stazione WHERE Nome = ?";
        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, station.getStationName());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
