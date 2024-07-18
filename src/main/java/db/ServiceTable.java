package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceTable {

    private final DBConnection dataSource;
    private int serviceID;

    public ServiceTable() {
        this.dataSource = new DBConnection();
    }

    public void addTicket(String journeyID, String departureStation, String destinationStation, LocalDate departureDate,
                          LocalTime departureTime, String typeTrain, float ticketPrice, String firstName, String lastName, String email) {

        String sql = "INSERT INTO Servizio (StazionePartenza, StazioneArrivo, NomePasseggero, CognomePasseggero, " +
                "TipoTreno, DataPartenza, OrarioPartenza, Prezzo, CodPercorso, Email, Durata, Chilometraggio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, departureStation);
            pstmt.setString(2, destinationStation);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, typeTrain);
            pstmt.setDate(6, Date.valueOf(departureDate));
            pstmt.setTime(7, Time.valueOf(departureTime));
            pstmt.setFloat(8, ticketPrice);
            pstmt.setString(9, journeyID);
            pstmt.setString(10, email);
            pstmt.setNull(11, Types.VARCHAR);
            pstmt.setNull(12, Types.INTEGER);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        serviceID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean haveVoucher(String voucher, String email) {
        String checkVoucherSql = "SELECT * FROM BuonoSconto WHERE CodBuonoSconto = ? AND Email = ? AND DataScadenza >= ?";
        String checkUsageSql = "SELECT * FROM Utilizzo WHERE CodBuonoSconto = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement checkVoucherStmt = conn.prepareStatement(checkVoucherSql);
             PreparedStatement checkUsageStmt = conn.prepareStatement(checkUsageSql)) {

            checkVoucherStmt.setString(1, voucher);
            checkVoucherStmt.setString(2, email);
            checkVoucherStmt.setDate(3, Date.valueOf(LocalDate.now()));
            ResultSet voucherResult = checkVoucherStmt.executeQuery();

            if (!voucherResult.next()) {
                return false;
            }

            checkUsageStmt.setString(1, voucher);
            ResultSet usageResult = checkUsageStmt.executeQuery();

            return !usageResult.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void useVoucher(String voucher) {
        String sql = "INSERT INTO Utilizzo (CodBuonoSconto, CodServizio, Data) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, voucher);
            pstmt.setInt(2, serviceID);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existGuest(String email) {
        String sql = "SELECT Email FROM Persona WHERE Email = ? AND (Password IS NULL OR TipoCliente = 'guest')";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateTotalPurchase(String email, float price) {
        String sql = "UPDATE Persona SET SpesaTotale = SpesaTotale + ? WHERE Email = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setFloat(1, price);
            pstmt.setString(2, email);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateGuest(String email, String firstName, String lastName, String address, String cf) {
        String checkSql = "SELECT Email FROM Persona WHERE Email = ?";
        String updateSql = "UPDATE Persona SET Nome = ?, Cognome = ?, Indirizzo = ?, Telefono = ?, CF = ?, Password = ?, SpesaTotale = ?, TipoPersona = 'cliente', TipoCliente = 'guest' WHERE Email = ?";
        String insertSql = "INSERT INTO Persona (Email, Nome, Cognome, Indirizzo, Telefono, CF, Password, SpesaTotale, TipoPersona, TipoCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'cliente', 'guest')";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {

            checkPstmt.setString(1, email);
            ResultSet rs = checkPstmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                    updatePstmt.setString(1, firstName);
                    updatePstmt.setString(2, lastName);
                    updatePstmt.setString(3, address);
                    updatePstmt.setNull(4, Types.VARCHAR);
                    updatePstmt.setString(5, cf);
                    updatePstmt.setNull(6, Types.VARCHAR);
                    updatePstmt.setNull(7, Types.FLOAT);
                    updatePstmt.setString(8, email);
                    updatePstmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                    insertPstmt.setString(1, email);
                    insertPstmt.setString(2, firstName);
                    insertPstmt.setString(3, lastName);
                    insertPstmt.setString(4, address);
                    insertPstmt.setNull(5, Types.VARCHAR);
                    insertPstmt.setString(6, cf);
                    insertPstmt.setNull(7, Types.VARCHAR);
                    insertPstmt.setNull(8, Types.FLOAT);
                    insertPstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
