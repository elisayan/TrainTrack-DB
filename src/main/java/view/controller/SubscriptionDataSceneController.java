package view.controller;

import controller.Controller;
import view.View;
import controller.SubscriptionDataController;
import model.Subscription;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SubscriptionDataSceneController extends AbstractSceneController {

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

}
