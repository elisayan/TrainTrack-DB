package view.controller;

import controller.Controller;
import controller.TimetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.View;

public class TimetableSceneController extends AbstractSceneController {
    
    @FXML
    private Label departureLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Button homeButton;

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
    private Label platformLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label timatableLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private GridPane timetableGridPane;

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
        
        int rows = 9;
        int columns = 7;

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns - 1; j++) {
                Label label = new Label("Row " + i + " Col " + j);
                timetableGridPane.add(label, j, i);
            }
        }

        //for (int x = 0; x < rows; x++) {
        //    boolean hasText = false;
        //    for (int y = 0; y < columns - 1; y++) {
        //        Text textNode = (Text) getNodeByRowColumnIndex(y, x, timetableGridPane);
        //        if (textNode != null && !textNode.getText().isEmpty()) {
        //            hasText = true;
        //            break;
        //        }
        //    }

        //    if (hasText) {
        //        timetableGridPane.add(new CheckBox(), columns - 1, x);
        //    }
        //}

    }

    //@FXML
    //private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
    //    for (javafx.scene.Node node : gridPane.getChildren()) {
    //        if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
    //            return node;
    //        }
    //    }
    //    return null;
    //}

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

}
