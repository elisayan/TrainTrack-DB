package view.controller;

import controller.Controller;
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
import view.View;

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

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        this.messageLabel.setText(Message.INSERT_INFO.toString());

        var currentUser = getController().getCurrentPerson().orElse(null);
        if (currentUser != null) {
            this.firstNameField.setText(currentUser.getName());
            this.lastNameField.setText(currentUser.getSurname());
            this.emailField.setText(currentUser.getEmail());
            this.addressField.setText(currentUser.getAddress());
            this.cfField.setText(currentUser.getCf());
        }
    }

    @FXML
    private void confirmClicked() throws IOException {
        this.messageLabel.setText("");
        if (vBox.getChildren().stream().noneMatch(t -> ((TextField) t).getText().isBlank() && !t.equals(voucherField))) {

            if (!this.voucherField.getText().isBlank()) {
                this.controller.checkEmail(ticket, this.firstNameField.getText(), this.lastNameField.getText(),
                        this.emailField.getText(), this.addressField.getText(), this.cfField.getText(), this.voucherField.getText());
            } else {
                this.controller.successful(ticket, this.firstNameField.getText(), this.lastNameField.getText(),
                        this.emailField.getText(), this.addressField.getText(), this.cfField.getText(), null);
            }
        } else {
            this.messageLabel.setText(Message.EMPTY_FIELD.toString());
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

    public void showMessage(Message message) {
        this.messageLabel.setText(message.toString());
    }
}
