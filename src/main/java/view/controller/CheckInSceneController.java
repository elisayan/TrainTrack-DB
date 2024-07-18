package view.controller;

import controller.CheckInController;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import model.Ticket;
import view.View;

import java.util.List;

public class CheckInSceneController extends AbstractSceneController {

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, String> journeyIDColumn;

    @FXML
    private TableColumn<Ticket, String> destinationColumn;

    @FXML
    private TableColumn<Ticket, String> timeColumn;

    @FXML
    private TableColumn<Ticket, String> dateColumn;

    @FXML
    private TableColumn<Ticket, Float> priceColumn;

    @FXML
    private TableColumn<Ticket, Boolean> checkInColumn;

    private final CheckInController controller = new CheckInController(this);

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);

        journeyIDColumn.setCellValueFactory(new PropertyValueFactory<>("journeyID"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationStation"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));

        checkInColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkInColumn));
        checkInColumn.setCellValueFactory(cellData -> cellData.getValue().checkedInProperty());

        checkInColumn.setCellFactory(tc -> {
            CheckBoxTableCell<Ticket, Boolean> cell = new CheckBoxTableCell<>();
            cell.setSelectedStateCallback(index -> ticketTable.getItems().get(index).checkedInProperty());

            cell.setOnMouseClicked(event -> {
                Ticket ticket = ticketTable.getItems().get(cell.getIndex());
                boolean newCheckedInStatus = !ticket.isCheckedIn();
                this.controller.updateCheckInStatus(ticket, newCheckedInStatus);
            });

            return cell;
        });

        controller.getCurrentPerson().ifPresent(currentPerson -> this.controller.getPersonTickets(currentPerson.getEmail()));
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    public void fillTicketTable(List<Ticket> tickets) {
        ticketTable.getItems().setAll(tickets);
    }

    public void showName(String personName) {
        this.messageLabel.setText(personName + " here are your tickets");
    }
}
