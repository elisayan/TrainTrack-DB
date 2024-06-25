package view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
        initializeTime(timeChoice);
    }

    static void initializeTime(ChoiceBox<String> timeChoice) {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;
        if (roundedMinute == 0) {
            hour++;
        }

        timeChoice.getItems().clear();
        for (int h = hour; h <= 23; h++) {
            for (int m = (h == hour) ? roundedMinute : 0; m < 60; m += 30) {
                timeChoice.getItems().add(String.format("%02d:%02d", h, m));
            }
        }
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
