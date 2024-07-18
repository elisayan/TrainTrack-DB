package db;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;

import model.Service;
import model.Subscription;

public class ServiceTable {

    private final DBConnection dataSource;
    private final String tableName;

    public ServiceTable() {
        this.dataSource = new DBConnection();
        this.tableName = "servizio";
    }

    public List<Subscription> subscriptionSearched(String departure, String destination, LocalDate beginningDate, String duration) {
       
        String query = "SELECT DISTINCT s.StazionePartenza, s.StazioneArrivo, s.TipoTreno, s.DataPartenza, s.Durata, t.Prezzo, s.Chilometraggio, s.CodPercorso " + 
                       "FROM " + tableName + " s, tipoabbonamento t " +  
                       "WHERE s.Durata = t.Durata " +
                       "AND s.Chilometraggio = t.Chilometraggio " +
                       "AND s.StazionePartenza = ? " +
                       "AND s.StazioneArrivo = ? " +
                       "AND s.DataPartenza = ? " +
                       "AND s.Durata = ? " + 
                       "ORDER BY t.prezzo"; 
    
        List<Subscription> subscriptions = new ArrayList<>();

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, departure);
            stmt.setString(2, destination);
            stmt.setString(4, duration);
            stmt.setDate(3, java.sql.Date.valueOf(beginningDate));
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Subscription subscription = new Subscription(
                    rs.getString("StazionePartenza"),
                    rs.getString("StazioneArrivo"),
                    rs.getDate("DataPartenza").toLocalDate(),
                    rs.getString("Durata"),
                    rs.getString("TipoTreno"),
                    rs.getFloat("Prezzo"),
                    rs.getFloat("Chilometraggio"),
                    rs.getInt("CodPercorso"));
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subscriptions;
    }

    public Service insertSubscriptionUser(Subscription subscriptionInfo, String name, String lastName, String email) {
        Service service = null;
        String sqlInsertSubscription = "INSERT INTO " + tableName +
                "(StazionePartenza, StazioneArrivo, Durata, DataPartenza, TipoTreno, " +
                "NomePasseggero, CognomePasseggero, Email, CodPercorso, Chilometraggio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement stmtInsertSubscription = connection.prepareStatement(sqlInsertSubscription, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtInsertSubscription.setString(1, subscriptionInfo.getDepartureStation());
                stmtInsertSubscription.setString(2, subscriptionInfo.getDestinationStation());
                stmtInsertSubscription.setString(3, subscriptionInfo.getDuration());
                stmtInsertSubscription.setDate(4, java.sql.Date.valueOf(subscriptionInfo.getBeginningDate()));
                stmtInsertSubscription.setString(5, subscriptionInfo.getType());
                stmtInsertSubscription.setString(6, name);
                stmtInsertSubscription.setString(7, lastName);
                stmtInsertSubscription.setString(8, email);
                stmtInsertSubscription.setInt(9, subscriptionInfo.getJourneyID());
                stmtInsertSubscription.setFloat(10, subscriptionInfo.getKm());
    
                int rowsInsertedSubscription = stmtInsertSubscription.executeUpdate();
    
                if (rowsInsertedSubscription > 0) {
                    // Retrieve the generated keys for servizio insertion
                    ResultSet generatedKeysSub = stmtInsertSubscription.getGeneratedKeys();
                    int serviceID = -1;
                    if (generatedKeysSub.next()) {
                        serviceID = generatedKeysSub.getInt(1);
                    }   
    
                    // Construct the Service object
                    service = new Service(serviceID,
                            subscriptionInfo.getDepartureStation(),
                            subscriptionInfo.getDestinationStation(),
                            name,
                            lastName,
                            email,
                            subscriptionInfo.getBeginningDate(),
                            subscriptionInfo.getDuration(),
                            subscriptionInfo.getType(),
                            subscriptionInfo.getPrice(),
                            subscriptionInfo.getKm(),
                            subscriptionInfo.getJourneyID());
                } 
    
             } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return service;
        }

    public Service insertSubscriptionGuest(Subscription subscriptionInfo, String name, String lastName, String email, String cf, String address, int phone) {
        Service service = null;
        String checkSql = "SELECT Email FROM Persona WHERE Email = ?";
        String sqlInsertGuest = "INSERT INTO Persona (Nome, Cognome, Email, CF, Indirizzo, Telefono, TipoPersona, TipoCliente) VALUES (?, ?, ?, ?, ?, ?, 'client', 'guest')";
        String sqlUpdateGuest = "UPDATE Persona SET Nome = ?, Cognome = ?, Indirizzo = ?, Telefono = ?, CF = ?, Password = ?, SpesaTotale = ?, TipoPersona = 'client', TipoCliente = 'guest' WHERE Email = ?";
        String sqlInsertSubscription = "INSERT INTO " + tableName +
                "(StazionePartenza, StazioneArrivo, Durata, DataPartenza, TipoTreno, " +
                "NomePasseggero, CognomePasseggero, Email, CodPercorso, Chilometraggio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement stmtCheckGuest = connection.prepareStatement(checkSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtUpdateGuest = connection.prepareStatement(sqlUpdateGuest, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtInsertGuest = connection.prepareStatement(sqlInsertGuest, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement stmtInsertSubscription = connection.prepareStatement(sqlInsertSubscription, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmtCheckGuest.setString(1, email);
            ResultSet rs = stmtCheckGuest.executeQuery();

            if (rs.next()) {
                // Guest exists, update the guest
                stmtUpdateGuest.setString(1, name);
                stmtUpdateGuest.setString(2, lastName);
                stmtUpdateGuest.setString(3, address);
                stmtUpdateGuest.setInt(4, phone);
                stmtUpdateGuest.setString(5, cf);
                stmtUpdateGuest.setNull(6, Types.VARCHAR); // Password is null
                stmtUpdateGuest.setNull(7, Types.FLOAT); // SpesaTotale is null
                stmtUpdateGuest.setString(8, email);
                stmtUpdateGuest.executeUpdate();
            } else {
                // Guest does not exist, insert new guest
                stmtInsertGuest.setString(1, name);
                stmtInsertGuest.setString(2, lastName);
                stmtInsertGuest.setString(3, email);
                stmtInsertGuest.setString(4, cf);
                stmtInsertGuest.setString(5, address);
                stmtInsertGuest.setInt(6, phone);
                stmtInsertGuest.executeUpdate();
            }

            // Insert into servizio table
            stmtInsertSubscription.setString(1, subscriptionInfo.getDepartureStation());
            stmtInsertSubscription.setString(2, subscriptionInfo.getDestinationStation());
            stmtInsertSubscription.setString(3, subscriptionInfo.getDuration());
            stmtInsertSubscription.setDate(4, java.sql.Date.valueOf(subscriptionInfo.getBeginningDate()));
            stmtInsertSubscription.setString(5, subscriptionInfo.getType());
            stmtInsertSubscription.setString(6, name);
            stmtInsertSubscription.setString(7, lastName);
            stmtInsertSubscription.setString(8, email);
            stmtInsertSubscription.setInt(9, subscriptionInfo.getJourneyID());
            stmtInsertSubscription.setFloat(10, subscriptionInfo.getKm());

            int rowsInsertedSubscription = stmtInsertSubscription.executeUpdate();

            if (rowsInsertedSubscription > 0) {
                // Retrieve the generated keys for servizio insertion
                ResultSet generatedKeysSub = stmtInsertSubscription.getGeneratedKeys();
                int serviceID = -1;
                if (generatedKeysSub.next()) {
                    serviceID = generatedKeysSub.getInt(1);
                }   

                // Construct the Service object
                service = new Service(serviceID,
                        subscriptionInfo.getDepartureStation(),
                        subscriptionInfo.getDestinationStation(),
                        name,
                        lastName,
                        email,
                        subscriptionInfo.getBeginningDate(),
                        subscriptionInfo.getDuration(),
                        subscriptionInfo.getType(),
                        subscriptionInfo.getPrice(),
                        subscriptionInfo.getKm(),
                        subscriptionInfo.getJourneyID());
            }

        } catch (SQLException e) { 
            e.printStackTrace();
            // Handle your exception appropriately
        }

        return service;
    }

    public boolean haveVoucher(String voucher, String email) {
        String checkVoucherSql = "SELECT * FROM BuonoSconto WHERE CodBuonoSconto = ? AND Email = ?";
        String checkUsageSql = "SELECT * FROM Utilizzo WHERE CodBuonoSconto = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement checkVoucherStmt = conn.prepareStatement(checkVoucherSql);
             PreparedStatement checkUsageStmt = conn.prepareStatement(checkUsageSql)) {

            checkVoucherStmt.setString(1, voucher);
            checkVoucherStmt.setString(2, email);
            ResultSet voucherResult = checkVoucherStmt.executeQuery();

            if (!voucherResult.next()) {
                return false;
            }

            checkUsageStmt.setString(1, voucher);
            ResultSet usageResult = checkUsageStmt.executeQuery();

            if (usageResult.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void useVoucher(String voucher, int serviceID) {
        String sql = "INSERT INTO Utilizzo (CodBuonoSconto, CodServizio, Data) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, voucher);
            pstmt.setInt(2, serviceID);
            pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted into Utilizzo: " + affectedRows + " rows, serviceID: " + serviceID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getVoucherValue(String voucher) {
        String query = "SELECT Valore FROM BuonoSconto WHERE CodBuonoSconto = ?";
        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, voucher);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("Valore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

}
