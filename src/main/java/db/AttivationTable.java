package db;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttivationTable {
    private final DBConnection dataSource;
    private final String tableName;

    public AttivationTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Attivazione";
    }

    public void subscribeNotification(String codPercorso, Person person) {
        try (Connection connection = dataSource.getMySQLConnection()) {
            List<String> notificationCods = getNotificationCods(connection, codPercorso);
           
            String insert = "INSERT INTO " + tableName +
                            " (email, CodNotifica) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insert)) {
                for (String codNotifica : notificationCods) {
                    stmt.setString(1, person.getEmail());
                    stmt.setString(2, codNotifica);
                    stmt.addBatch();
                }

                stmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void unsubscribeNotification(String codPercorso, Person person) {
        try (Connection connection = dataSource.getMySQLConnection()) {
            List<String> notificationCods = getNotificationCods(connection, codPercorso);

            String delete = "DELETE FROM " + tableName +
                            " WHERE email = ? AND CodNotifica = ?";
            try (PreparedStatement stmt = connection.prepareStatement(delete)) {
                for (String codNotifica : notificationCods) {
                    stmt.setString(1, person.getEmail());
                    stmt.setString(2, codNotifica);
                    stmt.addBatch();
                }

                stmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSubscribed(String codPercorso, Person person) {
        String query = "SELECT COUNT(*) FROM " + tableName + " a JOIN notifica n ON a.CodNotifica = n.CodNotifica WHERE a.email = ? AND n.CodPercorso = ?";

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, person.getEmail());
            pstmt.setString(2, codPercorso);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    private List<String> getNotificationCods(Connection connection, String codPercorso) throws SQLException {
        String query = "SELECT n.CodNotifica " +
                       "FROM Notifica n " +
                       "WHERE n.CodPercorso = ?";

        List<String> notificationCods = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, codPercorso);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String codNotifica = rs.getString("CodNotifica");
                    notificationCods.add(codNotifica);
                }
            }
        }

        return notificationCods;
    }
}
