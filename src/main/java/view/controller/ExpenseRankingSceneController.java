package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ExpenseRankingSceneController extends AbstractSceneController {

    @FXML
    private Label expenseLabel;

    @FXML
    private TableColumn<?, ?> expenseRankingColumn;

    @FXML
    private TableColumn<?, ?> expensesColumn;

    @FXML
    private TableView<?> expensesTable;

    @FXML
    private TableColumn<?, ?> firstNameColumn;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private Button loginButton;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }
}
