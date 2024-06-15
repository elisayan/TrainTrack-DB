package view.controller;

import db.PersonTable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import model.Person;

import java.util.List;

public class ExpenseRankingSceneController extends AbstractSceneController {

    @FXML
    private Label expenseLabel;

    @FXML
    private TableColumn<Person, Integer> expenseRankingColumn;

    @FXML
    private TableColumn<Person, Float> expensesColumn;

    @FXML
    private TableView<Person> expensesTable;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Button loginButton;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    public void initialize() {
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.expensesColumn.setCellValueFactory(new PropertyValueFactory<>("totalExspense"));
        this.expenseRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        centerTableColumn(expenseRankingColumn);
        centerTableColumn(firstNameColumn);
        centerTableColumn(lastNameColumn);
        centerTableColumn(expensesColumn);

        expensesColumn.setCellFactory(column -> new TableCell<Person, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f euro", item));
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        populateExpensesTable();
    }

    private <T> void centerTableColumn(TableColumn<Person, T> column) {
        column.setCellFactory(tc -> new TableCell<Person, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
    }

    private void populateExpensesTable() {
        PersonTable personTable = new PersonTable();
        List<Person> topSpenders = personTable.getTopFiveSpenders();

        for (int i = 0; i < topSpenders.size(); i++) {
            topSpenders.get(i).setRank(i + 1);
        }

        expensesTable.getItems().setAll(topSpenders);
    }
}
