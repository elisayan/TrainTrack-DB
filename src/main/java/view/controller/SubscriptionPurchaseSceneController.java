package view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SubscriptionPurchaseSceneController extends AbstractSceneController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField departureField;

    @FXML
    private TextField destinationField;

    @FXML
    private Button searchButton;

    @FXML
    private ChoiceBox<String> timeChoice;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void searchClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializeTime(timeChoice);
    }

    public void initialize() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
    }

}
