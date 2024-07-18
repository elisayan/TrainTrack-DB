package view.controller;

import controller.SubscriptionDataController;
import db.ServiceTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.Service;
import model.Subscription;

import java.io.IOException;


public class SubscriptionDataSceneController extends AbstractSceneController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField cfTextField;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Button homeButton;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField voucherField;

    private SubscriptionDataController subscriptionDataController;
    private Subscription subscription;


    @FXML
    public void initialize() {
        this.subscriptionDataController = new SubscriptionDataController(this);  // Inizializza il subscriptionDataController
    }

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void confirmClicked() throws IOException {
        String name = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String cf = cfTextField.getText();
        String address = addressTextField.getText();
        String voucher = voucherField.getText();
        int phone;

        // Validate fields
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty()
            || cf.isEmpty() || address.isEmpty()) {
            showError(MessageError.EMPTY_FIELD.toString());
            return;
        }

        try {
            phone = Integer.parseInt(phoneTextField.getText());
        } catch (NumberFormatException e) {
            showError("Invalid phone number format");
            return;
        }

        ServiceTable serviceTable = new ServiceTable();
        Person person = controller.getCurrentPerson();
        Service service = null;
        
        if (person != null) {
            service = serviceTable.insertSubscriptionUser(subscription, name, lastName, email);
            serviceTable.updateTotalPurchase(person.getEmail(), service.getPrice());
        } else {
            service = serviceTable.insertSubscriptionGuest(subscription, name, lastName, email, cf, address, phone);
        }

        if (service != null) {
            if (!voucher.isEmpty()) {
                if (serviceTable.haveVoucher(voucher, email)) {
                    serviceTable.useVoucher(voucher, service.getServiceID());
                } else {
                    showError("Invalid or used voucher");
                    return;
                }
            }

            subscriptionDataController.notifyView();
            bookedSuccessful();
        } else {
            showError("Failed to insert subscription");
        }
    }

    
    @FXML
    public void showError(String message) {
        errorLabel.setText(message);
    }
    
    public void bookedSuccessful() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/confirmationWindow.fxml"));
        Parent root = loader.load();

        ConfirmationWindowSceneController controller = loader.getController();
        controller.initialize(this.view, this.getController());
        controller.setRoot(root);

        final Stage stage = new Stage();
        stage.getIcons().add(new Image("/img/congrats.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.pane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();

        this.confirmButton.setDisable(true);
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription getSubscription() {
        return subscription;
    }

}
