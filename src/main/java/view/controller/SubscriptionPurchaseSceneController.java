package view.controller;

import controller.Controller;
import controller.SubscriptionPurchaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Subscription;
import javafx.scene.control.DateCell;
import view.View;

import java.time.LocalDate;
import java.util.*;

public class SubscriptionPurchaseSceneController extends AbstractSceneController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField departureField;

    @FXML
    private TextField destinationField;

    @FXML
    private Button searchButton;

    @FXML
    private ChoiceBox<String> timeChoice;

    @FXML
    private Label errorLabel;

    private SubscriptionPurchaseController controller;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    private void searchClicked() {
        this.controller.setListOfSubscriptions(getDepartureText(), getDestinationText(), getStartDate(), getDurationText());
    }

    public void showSubscriptionsSearched(List<List<Subscription>> subscriptionGroups) {
        SubscriptionSearchSceneController subscriptionSearchSceneController = 
            (SubscriptionSearchSceneController) this.view.switchScene("subscriptionSearchResults.fxml").get();
        subscriptionSearchSceneController.populateSearchTableSubs(subscriptionGroups);
    }

    @FXML
    private void initialize() {
        this.controller = new SubscriptionPurchaseController(this);
        timeChoice.getItems().addAll("Settimanale", "Mensile", "Annuale");
        timeChoice.setValue("Settimanale");

        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
    }

    public String getDepartureText() {
        return departureField.getText();
    }

    public String getDestinationText() {
        return destinationField.getText();
    }

    public String getDurationText() {
        return timeChoice.getValue();
    }

    public LocalDate getStartDate() {
        return datePicker.getValue();
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }
}
