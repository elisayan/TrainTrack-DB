package view.controller;

import controller.PassengerDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Ticket;

import java.io.IOException;

public class PassengerDetailSceneController extends AbstractSceneController {

    @FXML
    private BorderPane pane;

    @FXML
    private TextField cfField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField voucherField;

    @FXML
    private TextField addressField;

    @FXML
    private Button confirmButton;

    @FXML
    private VBox vBox;

    private final PassengerDetailController controller = new PassengerDetailController(this);

    private Ticket ticket;

    @FXML
    private void initialize() {
        this.messageLabel.setText(MessageError.INSERT_INFO.toString());
    }

    @FXML
    private void confirmClicked() throws IOException {
        this.messageLabel.setText("");
        if (vBox.getChildren().stream().noneMatch(t -> ((TextField)t).getText().isBlank()
                && !t.equals(voucherField))) {

            if (!this.voucherField.getText().isBlank()){
                this.controller.checkEmail(ticket.getJourneyID(), ticket.getDepartureStation(), ticket.getDestinationStation(),
                        ticket.getDepartureDate(), ticket.getDepartureTime(), ticket.getTypeTrain(), ticket.getTicketPrice(),
                        this.firstNameField.getText(), this.lastNameField.getText(), this.emailField.getText(),
                        this.addressField.getText(), this.cfField.getText(), this.voucherField.getText());
            } else {
                this.controller.successful(ticket.getJourneyID(), ticket.getDepartureStation(), ticket.getDestinationStation(),
                        ticket.getDepartureDate(), ticket.getDepartureTime(), ticket.getTypeTrain(), ticket.getTicketPrice(),
                        this.firstNameField.getText(), this.lastNameField.getText(), this.emailField.getText(),
                        this.addressField.getText(), this.cfField.getText());
            }
        } else {
            this.messageLabel.setText(MessageError.EMPTY_FIELD.toString());
        }
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    public void bookedSuccessful() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/confirmDialog.fxml"));
        Parent root = loader.load();

        ConfirmDialogController controller = loader.getController();
        controller.initialize(this.view, this.getController());
        controller.setRoot(root);

        final Stage stage = new Stage();
        stage.getIcons().add(new Image("/img/tick.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.pane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();

        this.confirmButton.setDisable(true);
    }

    public void selectedTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void showMessage(MessageError messageError) {
        this.messageLabel.setText(messageError.toString());
    }
}
