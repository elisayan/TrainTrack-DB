package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ticket;

import java.util.List;

public class SearchResultSceneController extends AbstractSceneController{

    @FXML
    private TableColumn<Ticket, String> departureColumn;

    @FXML
    private TableColumn<Ticket, String> destinationColumn;

    @FXML
    private TableColumn<Ticket, String> journeyIDColumn;

    @FXML
    private TableColumn<Ticket, Float> priceColumn;

    @FXML
    private TableView<Ticket> searchTable;

    @FXML
    private TableColumn<Ticket, String> timeColumn;

    @FXML
    private TableColumn<Ticket, String> typeColumn;

    @FXML
    void userClicked() {
        this.view.switchScene("login.fxml");
    }

    public void fillTicketTable(List<Ticket> ticketList){
        ObservableList<Ticket> data = FXCollections.observableArrayList(ticketList);

        journeyIDColumn.setCellValueFactory(new PropertyValueFactory<>("journeyID"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureStation"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationStation"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeTrain"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));

        searchTable.setItems(data);

        searchTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PassengerDetailSceneController passengerDetailSceneController = (PassengerDetailSceneController) this.view.switchScene("passengerDetailTicket.fxml").get();
                passengerDetailSceneController.selectedTicket(newValue);
            }
        });
    }

}
