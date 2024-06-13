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

    public PersonTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Persona";
    }

    public boolean signUpPerson(final Person person) {
        final Connection connection = dataSource.getMySQLConnection();

        Optional<PreparedStatement> statement = Optional.empty();
        final String insert = "insert into " + tableName
                + "(Nome, Cognome, CF, Indirizzo, Telefono, Email, Password, SpesaTotale, TipoPersona, TipoCliente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if (!this.findPerson(person.getEmail()).isEmpty()) {
            return false;
        }

        try {
            statement = Optional.of(connection.prepareStatement(insert));
            statement.get().setString(1, person.getName());
            statement.get().setString(2, person.getSurname());
            statement.get().setString(3, person.getCf());
            statement.get().setString(4, person.getAddress());
            statement.get().setString(5, person.getPhone());
            statement.get().setString(6, person.getEmail());
            statement.get().setString(7, person.getPassword());
            statement.get().setFloat(8, person.getTotalExspense());
            statement.get().setString(9, person.getPersonType());
            statement.get().setString(10, person.getClientType());
            statement.get().execute();
            return true;
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
                person.setPhone(result.getString("Telefono"));
                person.setEmail(result.getString("Email"));
                person.setPassword(result.getString("Password"));
                person.setTotalExspense(result.getFloat("SpesaTotale"));
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

    public Optional<Person> findPerson(final String email) {
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
                person.setPhone(resultSet.getString("Telefono"));
                person.setSurname(resultSet.getString("Cognome"));
                person.setTotalExspense(resultSet.getFloat("SpesaTotale"));
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

        return Optional.ofNullable(person);
    }
}
