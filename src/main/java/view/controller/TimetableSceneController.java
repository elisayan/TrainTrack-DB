package view.controller;

import controller.Controller;
import controller.TimetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.View;

public class TimetableSceneController extends AbstractSceneController {
    
    @FXML
    private Label departure1Label;

    @FXML
    private Label departure2Label;

    @FXML
    private Label departure3Label;

    @FXML
    private Label departure4Label;

    @FXML
    private Label departure5Label;

    @FXML
    private Label departure6Label;

    @FXML
    private Label departure7Label;

    @FXML
    private Label departure8Label;

    @FXML
    private Label departureLabel;

    @FXML
    private Label destination1Label;

    @FXML
    private Label destination2Label;

    @FXML
    private Label destination3Label;

    @FXML
    private Label destination4Label;

    @FXML
    private Label destination5Label;

    @FXML
    private Label destination6Label;

    @FXML
    private Label destination7Label;

    @FXML
    private Label destination8Label;

    @FXML
    private Label destinationLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Label idjourney1Label;

    @FXML
    private Label idjourney2Label;

    @FXML
    private Label idjourney3Label;

    @FXML
    private Label idjourney4Label;

    @FXML
    private Label idjourney5Label;

    @FXML
    private Label idjourney6Label;

    @FXML
    private Label idjourney7Label;

    @FXML
    private Label idjourney8Label;

    @FXML
    private Label idjourneyLabel;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox notification1CheckBox;

    @FXML
    private CheckBox notification2CheckBox;

    @FXML
    private CheckBox notification3CheckBox;

    @FXML
    private CheckBox notification4CheckBox;

    @FXML
    private CheckBox notification5CheckBox;

    @FXML
    private CheckBox notification6CheckBox;

    @FXML
    private CheckBox notification7CheckBox;

    @FXML
    private CheckBox notification8CheckBox;

    @FXML
    private Label notificationLabel;

    @FXML
    private Label platform1Label;

    @FXML
    private Label platform2Label;

    @FXML
    private Label platform3Label;

    @FXML
    private Label platform4Label;

    @FXML
    private Label platform5Label;

    @FXML
    private Label platform6Label;

    @FXML
    private Label platform7Label;

    @FXML
    private Label platform8Label;

    @FXML
    private Label platformLabel;

    @FXML
    private Label status1Label;

    @FXML
    private Label status2Label;

    @FXML
    private Label status3Label;

    @FXML
    private Label status4Label;

    @FXML
    private Label status5Label;

    @FXML
    private Label status6Label;

    @FXML
    private Label status7Label;

    @FXML
    private Label status8Label;

    @FXML
    private Label statusLabel;

    @FXML
    private Label timatableLabel;

    @FXML
    private Label time1Label;

    @FXML
    private Label time2Label;

    @FXML
    private Label time3Label;

    @FXML
    private Label time4Label;

    @FXML
    private Label time5Label;

    @FXML
    private Label time6Label;

    @FXML
    private Label time7Label;

    @FXML
    private Label time8Label;

    @FXML
    private Label timeLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    private TimetableController controller;

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        this.controller = new TimetableController(this, this.getController());
    }

    @FXML
    public void homeClicked() {
        this.view.switchScene("home.fxml");
    }

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }


}
