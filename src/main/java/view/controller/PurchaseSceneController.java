package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PurchaseSceneController extends  AbstractSceneController{

    @FXML
    private CheckBox bikeBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<?, ?> departureColumn;

    @FXML
    private TextField departureField;

    @FXML
    private TableColumn<?, ?> destinationColumn;

    @FXML
    private TextField destinationField;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<?, ?> journeyIDColumn;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox petBox;

    @FXML
    private Button searchButton;

    @FXML
    private Label searchesLabel;

    @FXML
    private Label supplementsLabel;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    void loginClicked() {
        this.view.switchScene("login.fxml");
    }

}
