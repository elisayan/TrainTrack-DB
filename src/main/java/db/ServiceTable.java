package db;

import java.util.*;
import java.time.LocalDate;
import model.Subscription;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServiceTable {

    private final DBConnection dataSource;
    private final String tableName;

    public ServiceTable() {
        this.dataSource = new DBConnection();
        this.tableName = "servizio";
    }

    public void addSubscription(String departure, String destination, LocalDate beginningDate, String duration, String type, float price) {

    }

    public List<Subscription> subscriptionSearched(String departure, String destination, LocalDate beginningDate, String duration) {
       
        String query = "SELECT s.StazionePartenza, s.StazioneArrivo, s.TipoTreno, s.DataPartenza, s.Durata, s.Prezzo " + 
                       "FROM " + tableName + " s " +
                       "WHERE s.StazionePartenza = ? " +
                       "AND s.StazioneArrivo = ? " +
                       "AND s.DataPartenza = ? " +
                       "AND s.Durata = ?" + 
                       "ORDER BY s.prezzo"; 
    
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
                    rs.getFloat("Prezzo"));
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subscriptions;
    }
}
