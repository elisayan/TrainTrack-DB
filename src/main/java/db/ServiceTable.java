package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ServiceTable {

    private final DBConnection dataSource;
    private int serviceID;
    private final Map<String, Float> voucherIssuedSpending;

    public ServiceTable() {
        this.dataSource = new DBConnection();
        this.voucherIssuedSpending = new HashMap<>();
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
        // Gestione del caso in cui il voucher è una stringa vuota
        if (voucher == null || voucher.trim().isEmpty()) {
            System.out.println("Il voucher è vuoto o non valido.");
            return;
        }

        String insertSql = "INSERT INTO Utilizzo (CodBuonoSconto, CodServizio, Data) VALUES (?, ?, ?)";
        String selectVoucherSql = "SELECT Importo, Email FROM BuonoSconto WHERE CodBuonoSconto = ?";
        String selectPriceSql = "SELECT Prezzo FROM Servizio WHERE CodServizio = ?";
        String updateSql = "UPDATE Servizio SET Prezzo = ? WHERE CodServizio = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement insertPstmt = conn.prepareStatement(insertSql);
             PreparedStatement selectVoucherPstmt = conn.prepareStatement(selectVoucherSql);
             PreparedStatement selectPricePstmt = conn.prepareStatement(selectPriceSql);
             PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {

            insertPstmt.setInt(1, Integer.parseInt(voucher));
            insertPstmt.setInt(2, serviceID);
            insertPstmt.setDate(3, Date.valueOf(LocalDate.now()));
            insertPstmt.executeUpdate();

            selectVoucherPstmt.setString(1, voucher);
            ResultSet voucherRs = selectVoucherPstmt.executeQuery();
            if (voucherRs.next()) {
                float voucherImporto = voucherRs.getFloat("Importo");
                String email = voucherRs.getString("Email");

                selectPricePstmt.setInt(1, serviceID);
                ResultSet priceRs = selectPricePstmt.executeQuery();
                if (priceRs.next()) {
                    float servicePrice = priceRs.getFloat("Prezzo");

                    float newPrice = servicePrice - voucherImporto;
                    if (newPrice < 0) {
                        newPrice = 0;
                    }

                    updatePstmt.setFloat(1, newPrice);
                    updatePstmt.setInt(2, serviceID);
                    updatePstmt.executeUpdate();
                }

                issueVouchersIfNecessary(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Il voucher deve essere un numero valido.");
        }
    }

    private void issueVouchersIfNecessary(String email) {
        String query = "SELECT SpesaTotale FROM Persona WHERE Email = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                float totalExpense = resultSet.getFloat("SpesaTotale");
                float previousSpending = voucherIssuedSpending.getOrDefault(email, 0f);
                int previousVoucherCount = (int) (previousSpending / 100);
                int currentVoucherCount = (int) (totalExpense / 100);
                int voucherCount = currentVoucherCount - previousVoucherCount;

                if (voucherCount > 0) {
                    String insertVoucherQuery = "INSERT INTO BuonoSconto (Importo, DataInizioValidita, DataScadenza, Email) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement voucherStmt = conn.prepareStatement(insertVoucherQuery)) {
                        for (int i = 0; i < voucherCount; i++) {
                            voucherStmt.setFloat(1, 10.0f);
                            voucherStmt.setDate(2, Date.valueOf(LocalDate.now()));
                            voucherStmt.setDate(3, Date.valueOf(LocalDate.now().plusMonths(1)));
                            voucherStmt.setString(4, email);
                            voucherStmt.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    voucherIssuedSpending.put(email, totalExpense);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isGuest(String email) {
        String sql = "SELECT Email, TipoCliente, Password FROM Persona WHERE Email = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tipoCliente = rs.getString("TipoCliente");
                String password = rs.getString("Password");
                return "guest".equals(tipoCliente) && password == null;
            } else {
                return false;
            }
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

    public void decreaseTotalSeats(String journeyID) {
        String getTrainCodeSql = "SELECT CodTreno FROM Percorso WHERE CodPercorso = ?";
        String decreaseSeatsSql = "UPDATE Treno SET PostiTotali = PostiTotali - 1 WHERE CodTreno = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement getTrainCodeStmt = conn.prepareStatement(getTrainCodeSql);
             PreparedStatement decreaseSeatsStmt = conn.prepareStatement(decreaseSeatsSql)) {

            getTrainCodeStmt.setString(1, journeyID);
            ResultSet rs = getTrainCodeStmt.executeQuery();
            if (rs.next()) {
                String codTreno = rs.getString("CodTreno");

                decreaseSeatsStmt.setString(1, codTreno);
                decreaseSeatsStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
