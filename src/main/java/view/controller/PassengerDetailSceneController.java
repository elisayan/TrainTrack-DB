package view.controller;

import controller.PassengerDetailController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.AvailableTicket;

public class PassengerDetailSceneController extends AbstractSceneController {

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
    private void confirmClicked() {
        //verifica se le info sono inserite correttamente
        this.messageLabel.setText("");
        boolean isFilled = vBox.getChildren().stream().noneMatch(t -> ((TextField)t).getText().isBlank() && !((TextField)t).equals(voucherField));
        if (isFilled) {
            //this.view.switchScene("");
            System.out.println("switch scene");
        } else {
            this.messageLabel.setText(MessageError.EMPTY_FIELD.toString());
            System.out.println("false switch scene");
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
