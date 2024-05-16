package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import model.Person;

public class PersonTable {
    private final DBConnection dataSource;
    private final String tableName;
    private Person person;

    public PersonTable() {
        this.dataSource = new DBConnection();
        this.tableName = "persona";
    }

    public boolean loginUser(final String email, final String password) {
        Person p = null;
        final Connection connection = dataSource.getMySQLConnection();

        Optional<PreparedStatement> statement = Optional.empty();
        final String query = "select * from " + tableName + " where email=? and password=? ";

        try {
            statement = Optional.of(connection.prepareStatement(query));
            statement.get().setString(1, email);
            statement.get().setString(2, password);
            final ResultSet result = statement.get().executeQuery();

            if (result.next()) {
                person = new Person();
                person.setCf(result.getString("CF"));
                person.setName(result.getString("nome"));
                person.setSurname(result.getString("cognome"));
                person.setAddress(result.getString("indirizzo"));
                person.setPhone(result.getInt(12345));
                person.setEmail(result.getString("email"));
                person.setPassword(result.getString("password"));
                person.setTotalExspense(result.getFloat(500));
                this.person = p;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!statement.isEmpty()) {
                    statement.get().close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
