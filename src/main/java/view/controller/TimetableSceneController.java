package view.controller;

//import com.mysql.cj.xdevapi.Statement;

import controller.Controller;
import controller.TimetableController;
import db.ThroughTable;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.JourneyInfo;
import view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

public class TimetableSceneController extends AbstractSceneController {
    
    @FXML
    private Label departureLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Button homeButton;

    @FXML
    private TextField stationField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label idjourneyLabel;

    @FXML
    private Button loginButton;

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

    @FXML
    private final DBConnection dataSource = new DBConnection();
    
    final private int rows = 9;
    final private int columns = 7;

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        this.controller = new TimetableController(this, this.getController());
    }

    @FXML
    public void searchClicked() throws SQLException {
    String station = stationField.getText().trim();    
    if (!station.isBlank() && isFieldsExist(station)) {
        try (Connection conn = dataSource.getMySQLConnection()) {
            // Pulizia del GridPane
            cleanGridPane(timetableGridPane);
            cleanLabel(errorLabel);
            // Popolamento del GridPane
            populateGridPaneFromDatabase(timetableGridPane, station);

            centerAllLabels(timetableGridPane);

            for (int i = 1; i < rows; i++) {
                addCheckBoxIfLabelHasText(timetableGridPane, 0, i, columns - 1);
                disableCheckBoxIfUserNotLoggedIn(timetableGridPane, i, columns -1, getController());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        cleanGridPane(timetableGridPane);
        errorLabel.setText(MessageError.STATION_NOT_EXIST.toString());
        }
    }

    @FXML
    public void cleanGridPane(GridPane gridPane) {
        // Remove all children except the headers in the first row
        gridPane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });
    }
    

    @FXML
    private void cleanLabel(Label label) {
        label.setText("");
    }

    @FXML
    private boolean isFieldsEmpty() {
        return stationField.getText().isBlank();
    }

    @FXML
    private boolean isFieldsExist(String station) {
        String selectStationQuery = "SELECT CodStazione FROM stazione WHERE Nome = ?";

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectStationQuery)) {
            pstmt.setString(1, station);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if a result is found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void populateGridPaneFromDatabase(GridPane gridPane, String station) throws SQLException {
        try (Connection conn = dataSource.getMySQLConnection()) {
            int row = 1;
            List<JourneyInfo> journeyInfos = ThroughTable.getJourneyInfos(conn, station);

           
            for (JourneyInfo journeyInfo : journeyInfos) {
                Label labelPercorso = new Label(journeyInfo.getCodPercorso());
                Label labelBinario = new Label(String.valueOf(journeyInfo.getBinario()));
                Label labelOrarioPartenzaPrevisto = new Label(journeyInfo.getOrarioPartenzaPrevisto());
                Label labelStazionePartenza = new Label(journeyInfo.getStazionePartenzaNome());
                Label labelStazioneArrivo = new Label(journeyInfo.getStazioneArrivoNome());
                Label labelStatoArrivo = new Label(journeyInfo.getStatoArrivo());

                gridPane.add(labelPercorso, 0, row);
                gridPane.add(labelStazionePartenza, 1, row);
                gridPane.add(labelStazioneArrivo, 2, row);
                gridPane.add(labelOrarioPartenzaPrevisto, 3, row);
                gridPane.add(labelBinario, 4, row);
                gridPane.add(labelStatoArrivo, 5, row);

                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void centerAllLabels(GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
                GridPane.setValignment(node, VPos.CENTER);
                ((Label) node).setAlignment(Pos.CENTER);
            }
        }
    }

    @FXML
        private void addCheckBoxIfLabelHasText(GridPane gridPane, int labelCol, int row, int checkBoxCol) {
        Label label = (Label) getNodeFromGridPane(gridPane, labelCol, row);
        if (label != null && !label.getText().isEmpty()) {
            CheckBox checkBox = new CheckBox();
            gridPane.add(checkBox, checkBoxCol, row); 
            GridPane.setHalignment(checkBox, HPos.CENTER);
            GridPane.setValignment(checkBox, VPos.CENTER);
        }
    }
    @FXML
    private Object getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex == col && rowIndex == row) {
                return node;
            }
        }
        return null;
    }

    @FXML
    public void disableCheckBoxIfUserNotLoggedIn(GridPane gridPane, int row, int column, Controller controller) {
        CheckBox checkBox = (CheckBox) getNodeFromGridPane(gridPane, column, row);
        if (controller.getCurrentPerson() == null) {
            checkBox.setDisable(true);
        }
    }
    
    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

}
