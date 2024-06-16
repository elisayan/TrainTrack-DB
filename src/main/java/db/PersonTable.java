package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import model.Person;

public class PersonTable {
    private final DBConnection dataSource;
    private final String tableName;

    public PersonTable() {
        this.dataSource = new DBConnection();
        this.tableName = "Persona";
    }

    public boolean signUpPerson(Person person) {
        String insert = "INSERT INTO " + tableName +
                " (Nome, Cognome, CF, Indirizzo, Telefono, Email, Password, SpesaTotale, TipoPersona, TipoCliente) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(insert)) {
          
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

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(String email, String password) {
        String query = "SELECT * FROM " + tableName + " WHERE Email=? AND Password=?";

        try (Connection connection = dataSource.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Person person = new Person();
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
        }
        return false;
    }

    public Optional<Person> findPerson(String email) {
        String query = "SELECT * FROM " + tableName + " WHERE Email=?";

        try (Connection connection = dataSource.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Person person = new Person();
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
                return Optional.of(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Person> getTopFiveSpenders() {
        List<Person> topSpenders = new LinkedList<>();
        String query = "SELECT Nome, Cognome, SpesaTotale FROM " + tableName
                + " WHERE SpesaTotale >= 1000 ORDER BY SpesaTotale DESC LIMIT 5";

        try (Connection connection = dataSource.getMySQLConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Person person = new Person();
                person.setName(resultSet.getString("Nome"));
                person.setSurname(resultSet.getString("Cognome"));
                person.setTotalExspense(resultSet.getFloat("SpesaTotale"));
                topSpenders.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topSpenders;
    }
}
