package db;

import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "SELECT CodPercorso, StazionePartenza, StazioneArrivo, TipoTreno, OrarioPartenza, Prezzo, DataPartenza " +
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

                Ticket ticket = new Ticket(codPercorso, stazionePartenza, stazioneArrivo, tipoTreno, orarioPartenza, prezzo, dataPartenza);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
