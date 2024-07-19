package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import model.Person;

import java.util.List;

import controller.ExpenseRankingController;

public class ExpenseRankingSceneController extends AbstractSceneController {

    @FXML
    private TableColumn<Person, Integer> expenseRankingColumn;

    @FXML
    private TableColumn<Person, Float> expensesColumn;

    @FXML
    private TableView<Person> expensesTable;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    private final ExpenseRankingController controller = new ExpenseRankingController(this);

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    public void initialize() {
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.expensesColumn.setCellValueFactory(new PropertyValueFactory<>("totalExpense"));
        this.expenseRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        centerTableColumn(expenseRankingColumn);
        centerTableColumn(firstNameColumn);
        centerTableColumn(lastNameColumn);
        centerTableColumn(expensesColumn);

        expensesColumn.setCellFactory(column -> new TableCell<>() {
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

        controller.updateSpendersInfo();
    }

    private <T> void centerTableColumn(TableColumn<Person, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
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

    public void populateExpensesTable(List<Person> topSpenders) {
        expensesTable.getItems().setAll(topSpenders);
        for (int i = 0; i < topSpenders.size(); i++) {
            topSpenders.get(i).setRank(i + 1);
        }
    }
}
