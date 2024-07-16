package view.controller;

import controller.PassengerDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AvailableTicket;

import java.io.IOException;

public class PassengerDetailSceneController extends AbstractSceneController {

    @FXML
    private BorderPane pane;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cfField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField voucherField;

    @FXML
    private VBox vBox;

    private final PassengerDetailController controller = new PassengerDetailController(this);

    @FXML
    private void initialize() {
        this.messageLabel.setText(MessageError.INSERT_INFO.toString());
    }

    @FXML
    private void confirmClicked() throws IOException {
        this.messageLabel.setText("");
        if (vBox.getChildren().stream().noneMatch(t -> ((TextField)t).getText().isBlank()
                && !t.equals(voucherField))) {
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
        } else {
            this.messageLabel.setText(MessageError.EMPTY_FIELD.toString());
        }
    }

    @FXML
    private void userClicked() {
        this.view.switchScene("login.fxml");
    }

    public void selectedTicket(AvailableTicket newValue) {
        //prendere le info di input e salvare al model di service table
        this.controller.saveTicket(newValue.getJourneyID(), newValue.getDepartureStation(), newValue.getDestinationStation(),
                newValue.getDepartureTime(), newValue.getTypeTrain(), newValue.getTicketPrice(), this.firstNameField.getText(),
                this.lastNameField.getText(), this.emailField.getText(), this.cfField.getText(), this.addressField.getText(),
                this.phoneField.getText(), this.voucherField.getText());
    }
}
