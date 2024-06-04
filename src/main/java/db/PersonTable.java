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
        this.tableName = "Persone";
    }

    public boolean loginUser(final String email, final String password) {
        Person person = null;
        final Connection connection = dataSource.getMySQLConnection();

        Optional<PreparedStatement> statement = Optional.empty();
        final String query = "select * from " + tableName + " where Email=? and Password=? ";

        try {
            statement = Optional.of(connection.prepareStatement(query));
            statement.get().setString(1, email);
            statement.get().setString(2, password);
            final ResultSet result = statement.get().executeQuery();

            if (result.next()) {
                person = new Person();
                person.setCf(result.getString("CF"));
                person.setName(result.getString("Nome"));
                person.setSurname(result.getString("Cognome"));
                person.setAddress(result.getString("Indirizzo"));
                person.setPhone(result.getInt("Telefono"));
                person.setEmail(result.getString("Email"));
                person.setPassword(result.getString("Password"));
                person.setTotalExspense(result.getFloat("SpesaTotale"));
                this.person = person;
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

    public Person findPerson(final String email) {
        Person person = null;
        final Connection connection = this.dataSource.getMySQLConnection();

        Optional<PreparedStatement> statement = Optional.empty();
        final String query = "select * from " + tableName + " where Email=?";

        try {
            statement = Optional.of(connection.prepareStatement(query));
            statement.get().setString(1, email);
            final ResultSet resultSet = statement.get().executeQuery();

            if (resultSet.next()) {
                person = new Person();
                person.setAddress(resultSet.getString("Indirizzo"));
                person.setCf(resultSet.getString("CF"));
                person.setClientType(resultSet.getString("TipoCliente"));
                person.setEmail(resultSet.getString("Email"));
                person.setName(resultSet.getString("Nome"));
                person.setPassword(resultSet.getString("Password"));
                person.setPersonType(resultSet.getString("TipoPersona"));
                person.setPhone(resultSet.getInt("Telefono"));
                person.setSurname(resultSet.getString("Cognome"));
                person.setTotalExspense(resultSet.getFloat("SpesaTotale"));                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
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

        return person;
    }

    public Person getCurrentPerson(){
        return this.person;
    }
}
