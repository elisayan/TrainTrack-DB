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
        String insert = "INSERT INTO " + tableName
                + " (Nome, Cognome, CF, Indirizzo, Telefono, Email, Password, SpesaTotale, TipoPersona, TipoCliente) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement statement = connection.prepareStatement(insert)) {

            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setString(3, person.getCf());
            statement.setString(4, person.getAddress());
            statement.setString(5, person.getPhone());
            statement.setString(6, person.getEmail());
            statement.setString(7, person.getPassword());
            statement.setFloat(8, person.getTotalExpense());
            statement.setString(9, person.getPersonType());
            statement.setString(10, person.getClientType());

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
                person.setCf(resultSet.getString("CF"));
                person.setName(resultSet.getString("Nome"));
                person.setSurname(resultSet.getString("Cognome"));
                person.setAddress(resultSet.getString("Indirizzo"));
                person.setPhone(resultSet.getString("Telefono"));
                person.setEmail(resultSet.getString("Email"));
                person.setPassword(resultSet.getString("Password"));
                person.setTotalExpense(resultSet.getFloat("SpesaTotale"));

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
                person.setTotalExpense(resultSet.getFloat("SpesaTotale"));
                return Optional.of(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Person> getAllSpendersRanking() {
        List<Person> spendersRanking = new LinkedList<>();
        String query = "SELECT Nome, Cognome, SpesaTotale, Email FROM " + tableName
                + " WHERE TipoPersona = 'cliente' AND TipoCliente = 'utente' "
                + "ORDER BY SpesaTotale DESC";

        try (Connection connection = dataSource.getMySQLConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Person person = new Person();
                person.setName(resultSet.getString("Nome"));
                person.setSurname(resultSet.getString("Cognome"));
                person.setTotalExpense(resultSet.getFloat("SpesaTotale"));
                person.setEmail(resultSet.getString("Email"));
                spendersRanking.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return spendersRanking;
    }
}
