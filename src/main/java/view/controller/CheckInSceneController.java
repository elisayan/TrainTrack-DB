package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import model.Ticket;

import java.util.List;

public class CheckInSceneController extends AbstractSceneController{

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    void userClicked() {
        this.view.switchScene("login.fxml");
    }

    public void fillTicketTable(List<Ticket> tickets) {
        //riempe la tabella
    }
}
