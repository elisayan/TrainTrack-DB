package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class SearchSceneController extends AbstractSceneController{

    @FXML
    private TableColumn<?, ?> departureColumn;

    @FXML
    private TableColumn<?, ?> destinationColumn;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<?, ?> journeyIDColumn;

    @FXML
    private Button loginButton;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableView<?> seachTable;

    @FXML
    private Label searchLabel;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    void loginClicked() {
        this.view.switchScene("login.fxml");
    }

}
