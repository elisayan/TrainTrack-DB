package view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PurchaseSceneController  extends AbstractSceneController implements Initializable{

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
    private Label errorLabel;

    @FXML
    private Label searchesLabel;

    @FXML
    private Label supplementsLabel;

    @FXML
    private ChoiceBox<String> timeBox;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private ChoiceBox<String> trainTypeBox;

    @FXML
    void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    void searchClicked(MouseEvent event) {
        //se nel db trova i treni che combaciano con la partenza, destinazione combaciata, allora setta
        //l'error label, in messaggio di errore
        //se trova almeno uno ritorna la lista disponibile, quindi cambia schermata
        // if (){}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trainTypeBox.getItems().addAll("Regionale", "Intercity", "Frecciarossa");
        trainTypeBox.setValue("Regionale");

        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;
        if (roundedMinute == 0) {
            hour++;
        }

        timeBox.getItems().clear();
        for (int h = hour; h <= 23; h++) {
            for (int m = (h == hour) ? roundedMinute : 0; m < 60; m += 30) {
                timeBox.getItems().add(String.format("%02d:%02d", h, m));
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
