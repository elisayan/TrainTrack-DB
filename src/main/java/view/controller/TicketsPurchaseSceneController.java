package view.controller;

import controller.PurchaseTicketController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketsPurchaseSceneController extends AbstractSceneController {

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

    private PurchaseTicketController controller;

    @FXML
    private void initialize() {
        this.controller = new PurchaseTicketController(this);

        this.datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        this.trainTypeBox.getItems().addAll("Regionale", "Intercity", "Frecciarossa");
        this.trainTypeBox.setValue("Regionale");

        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;
        if (roundedMinute == 0) {
            hour++;
        }

        this.timeBox.getItems().clear();
        for (int h = hour; h <= 23; h++) {
            for (int m = (h == hour) ? roundedMinute : 0; m < 60; m += 30) {
                this.timeBox.getItems().add(String.format("%02d:%02d", h, m));
            }
        }
    }

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void searchClicked() {
        //se nel db trova i treni che combaciano con la partenza, destinazione combaciata, allora setta
        //l'error label, in messaggio di errore
        //se trova almeno uno ritorna la lista disponibile, quindi cambia schermata
        // if (){}
        this.errorLabel.setText("");
        this.controller.setAvailableTickets(departureField.getText(), destinationField.getText(), trainTypeBox.getValue(),
                datePicker.getValue(), timeBox.getValue(), petBox.isSelected(), bikeBox.isSelected());
    }

    public void showAvailableTickets() {
        this.view.switchScene("searchResults.fxml");
    }

    public void showMessage() {
        this.errorLabel.setText(MessageError.TICKET_NOT_EXIST.toString());
    }
}
