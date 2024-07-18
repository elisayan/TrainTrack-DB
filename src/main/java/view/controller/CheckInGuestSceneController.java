package view.controller;

import controller.CheckInGuestController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Ticket;

import java.util.List;

public class CheckInGuestSceneController extends AbstractSceneController{

    @FXML
    private TextField emailField;

    @FXML
    private Label messageLabel;

    private final CheckInGuestController controller = new CheckInGuestController(this);

    @FXML
    private void initialize() {
        this.showMessage(MessageError.SUGGESTION_EMAIL);
    }

    @FXML
    private void confirmClicked() {
        this.messageLabel.setText("");
        if (this.emailField.getText().isEmpty()) {
            this.showMessage(MessageError.INSERT_MAIL);
        } else {
            this.controller.checkPerson(this.emailField.getText());
        }
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    public void showTickets(List<Ticket> tickets) {
        CheckInSceneController checkInSceneController = (CheckInSceneController) this.view.switchScene("checkin.fxml").get();
        checkInSceneController.fillTicketTable(tickets);
    }

    public void showMessage(MessageError messageError) {
        this.messageLabel.setText(messageError.toString());
    }
}
