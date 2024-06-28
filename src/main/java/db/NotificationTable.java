package db;

import model.JourneyInfo;
import model.NotificationInfo;

import view.controller.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


public class NotificationTable {
    private final DBConnection dataSource;
    private final String tableName;

    public NotificationTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Notifica";
    }
    
    
    public List<NotificationInfo> getNotificationInfos(Connection connection, String codNotifica) throws SQLException {
        String selectNotificaQuery = "SELECT n.CodNotifica, n.Descrizione, n.CodPercorso, a.email " + 
                                     "FROM notifica n " + 
                                     "JOIN attivazione a ON n.CodNotifica = a.CodNotifica " + 
                                     "WHERE n.CodNotifica = ?";

        List<NotificationInfo> notificationInfos = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(selectNotificaQuery)) {
            pstmt.setString(1, codNotifica);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String descrizione = rs.getString("Descrizione");
                    String codPercorso = rs.getString("CodPercorso");
                    String email = rs.getString("email");

                    NotificationInfo notificationInfo = new NotificationInfo(codNotifica, descrizione, codPercorso, email);
                    notificationInfos.add(notificationInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notificationInfos;
    }
}
