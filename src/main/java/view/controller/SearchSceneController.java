package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.AvailableTicket;

import java.util.List;

public class SearchSceneController extends AbstractSceneController{

    @FXML
    private TableColumn<AvailableTicket, String> departureColumn;

    @FXML
    private TableColumn<AvailableTicket, String> destinationColumn;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<AvailableTicket, String> journeyIDColumn;

    @FXML
    private Button loginButton;

    @FXML
    private TableColumn<AvailableTicket, Float> priceColumn;

    @FXML
    private TableView<AvailableTicket> searchTable;

    @FXML
    private Label searchLabel;

    @FXML
    private TableColumn<AvailableTicket, String> timeColumn;

    @FXML
    private TableColumn<AvailableTicket, String> typeColumn;

    @FXML
    void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    public void fillTicketTable(List<AvailableTicket> ticketList){
        ObservableList<AvailableTicket> data = FXCollections.observableArrayList(ticketList);

        System.out.println(ticketList.stream().iterator().next().getJourneyID());

        journeyIDColumn.setCellValueFactory(new PropertyValueFactory<>("journeyID"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        searchTable.setItems(data);
    }
}
