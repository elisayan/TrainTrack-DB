package view.controller;

import controller.PurchaseTicketController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.AvailableTicket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> populateTimeBox(newValue));

        this.datePicker.setValue(LocalDate.now());
    }

    private void populateTimeBox(LocalDate date) {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;
        if (roundedMinute == 0) {
            hour++;
        }

        this.timeBox.getItems().clear();
        if (date.isEqual(LocalDate.now())) {
            int finalHour = hour;
            this.timeBox.getItems().addAll(
                    IntStream.range(hour, 24).boxed()
                            .flatMap(h -> IntStream.iterate((h == finalHour) ? roundedMinute : 0, m -> m < 60, m -> m + 30)
                                    .mapToObj(m -> String.format("%02d:%02d", h, m)))
                            .toList()
            );
        } else {
            this.timeBox.getItems().addAll(
                    IntStream.range(0, 24)
                            .boxed()
                            .flatMap(h -> IntStream.iterate(0, m -> m < 60, m -> m + 30)
                                    .mapToObj(m -> String.format("%02d:%02d", h, m)))
                            .toList()
            );
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

    public void showAvailableTickets(List<AvailableTicket> tickets) {
        SearchSceneController searchSceneController = new SearchSceneController();
        searchSceneController.fillTicketTable(tickets);
        this.view.switchScene("searchResults.fxml");
    }

    public void showMessage() {
        this.errorLabel.setText(MessageError.TICKET_NOT_EXIST.toString());
    }
}
