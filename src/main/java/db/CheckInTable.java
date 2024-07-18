package db;

import model.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class CheckInTable {

    private final DBConnection dataSource;

    public CheckInTable() {
        this.dataSource = new DBConnection();
    }

    public String getPersonName(String email) {
        String sql = "SELECT Nome FROM Persona WHERE Email = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Ticket> getPersonTicket(String email) {
        String sql = "SELECT CodPercorso, StazionePartenza, StazioneArrivo, TipoTreno, OrarioPartenza, Prezzo, DataPartenza, CodServizio " +
                "FROM Servizio WHERE Email = ?";
        List<Ticket> tickets = new LinkedList<>();

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String codPercorso = rs.getString("CodPercorso");
                String stazionePartenza = rs.getString("StazionePartenza");
                String stazioneArrivo = rs.getString("StazioneArrivo");
                String tipoTreno = rs.getString("TipoTreno");
                LocalTime orarioPartenza = rs.getTime("OrarioPartenza").toLocalTime();
                float prezzo = rs.getFloat("Prezzo");
                LocalDate dataPartenza = rs.getDate("DataPartenza").toLocalDate();
                int codServizio = rs.getInt("CodServizio");

                Ticket ticket = new Ticket(codPercorso, stazionePartenza, stazioneArrivo, tipoTreno, orarioPartenza, prezzo, dataPartenza);
                ticket.setEmail(email);
                ticket.setCodServizio(codServizio);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    public void insertCheckIn(String email, int codServizio) {
        String sql = "INSERT INTO CheckIn (Data, Ora, Email, codServizio) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt.setTime(2, Time.valueOf(java.time.LocalTime.now()));
            pstmt.setString(3, email);
            pstmt.setInt(4, codServizio);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isCheckInValid(int codServizio) {
        String sqlCheckInTime = "SELECT CodServizio FROM Servizio WHERE CodServizio = ? AND OrarioPartenza > ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmtCheckInTime = conn.prepareStatement(sqlCheckInTime)) {

            LocalTime checkInTimeLimit = LocalTime.now().plusMinutes(5);
            pstmtCheckInTime.setInt(1, codServizio);
            pstmtCheckInTime.setTime(2, Time.valueOf(checkInTimeLimit));

            ResultSet rsCheckInTime = pstmtCheckInTime.executeQuery();
            if (rsCheckInTime.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean isCheckInExist(int codServizio, String email) {
        String sqlCheckInExists = "SELECT 1 FROM CheckIn WHERE CodServizio = ? AND Email = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmtCheckInExists = conn.prepareStatement(sqlCheckInExists)) {

            pstmtCheckInExists.setInt(1, codServizio);
            pstmtCheckInExists.setString(2, email);

            ResultSet rsCheckInExists = pstmtCheckInExists.executeQuery();
            if (rsCheckInExists.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
