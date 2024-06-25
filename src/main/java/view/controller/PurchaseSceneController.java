package view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseSceneController  extends AbstractSceneController implements Initializable{

    @FXML
    private CheckBox bikeBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<?, ?> departureColumn;

    @FXML
    private TextField departureField;

    @FXML
    private TableColumn<?, ?> destinationColumn;

    @FXML
    private TextField destinationField;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<?, ?> journeyIDColumn;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox petBox;

    @FXML
    private Button searchButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label searchesLabel;

    @FXML
    private Label supplementsLabel;

    @FXML
    private ChoiceBox<?> timeBox;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private ChoiceBox<String> trainTypeBox;

    @FXML
    void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    @FXML
    void searchClicked(MouseEvent event) {
        //se nel db trova i treni che combaciano con la partenza, destinazione combaciata, allora setta
        //l'error label, in messaggio di errore
        //se trova almeno uno ritorna la lista disponibile, quindi cambia schermata
        // if (){}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trainTypeBox.getItems().addAll("Regionale", "Intercity", "Frecciarossa");
        trainTypeBox.setValue("Regionale");
    }
}
