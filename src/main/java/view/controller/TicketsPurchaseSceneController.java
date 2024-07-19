package view.controller;

import controller.PurchaseTicketController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class TicketsPurchaseSceneController extends AbstractSceneController {

    @FXML
    private CheckBox bikeBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField departureField;

    @FXML
    private TextField destinationField;

    @FXML
    private CheckBox petBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> timeBox;

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
        this.timeBox.setVisibleRowCount(5);
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void searchClicked() {
        this.errorLabel.setText("");
        this.controller.setAvailableTickets(departureField.getText(), destinationField.getText(), trainTypeBox.getValue(),
                datePicker.getValue(), timeBox.getValue(), petBox.isSelected() && bikeBox.isSelected() ? 2 : petBox.isSelected() || bikeBox.isSelected() ? 1 : 0);
    }

    public void showAvailableTickets(List<Ticket> tickets) {
        SearchResultSceneController searchResultSceneController = (SearchResultSceneController) this.view.switchScene("searchResults.fxml").get();
        searchResultSceneController.fillTicketTable(tickets);
    }

    public void showMessage() {
        this.errorLabel.setText(Message.TICKET_NOT_EXIST.toString());
    }
}
