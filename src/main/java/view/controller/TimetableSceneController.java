package view.controller;

import controller.Controller;
import controller.TimetableController;
import db.DBConnection;
import db.AttivationTable;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Journey;
import model.JourneyInfo;
import model.Person;
import model.Station;
import view.View;

import java.sql.SQLException;

import java.util.*;


public class TimetableSceneController extends AbstractSceneController {

    @FXML
    private TextField stationField;

    @FXML
    private Label errorLabel;

    @FXML
    private GridPane timetableGridPane;

    private TimetableController controller;
    private AttivationTable attivationTable;

    @FXML
    private final DBConnection dataSource = new DBConnection();

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        this.controller = new TimetableController(this, this.getController());
        this.attivationTable = new AttivationTable(); 
    }

    @FXML
    public void searchClicked() {
        try {
            controller.searchStation(stationField.getText().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTimetableView(Station station) {
        try {
            cleanGridPane(timetableGridPane);
            cleanLabel(errorLabel);
            controller.updateJourneyInfo(station);
            centerAllLabels(timetableGridPane);

            int rows = 9;
            for (int i = 1; i < rows; i++) {
                int columns = 7;
                addNotVisibleCheckBoxes(timetableGridPane, columns - 1, i);
                setCheckBoxVisibleIfLabelHasText(timetableGridPane, 0, i, columns - 1);
                disableCheckBoxIfUserNotLoggedIn(timetableGridPane, i, columns - 1, getController());
                setCheckBoxSelected(timetableGridPane, i, columns - 1, getController());
            }
        } catch (SQLException e) {
            showError(MessageError.ERROR.toString());
        }
    }

    public void showError(String message) {
        cleanGridPane(timetableGridPane);
        errorLabel.setText(message);
    }

    private void addNotVisibleCheckBoxes(GridPane gridPane, int col, int row) {
        CheckBox checkBox = new CheckBox();
        gridPane.add(checkBox, col, row); 
        GridPane.setHalignment(checkBox, HPos.CENTER);
        GridPane.setValignment(checkBox, VPos.CENTER);
        checkBox.setOnMouseClicked(this::checkBoxClicked);
        checkBox.setVisible(false);
    }

    @FXML
    public void checkBoxClicked(MouseEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        int row = GridPane.getRowIndex(checkBox);
        Label codPercorsoLabel = (Label) getNodeFromGridPane(timetableGridPane, 0, row);
        String codPercorso = codPercorsoLabel.getText();
        Person person = getController().getCurrentPerson().orElse(null);
        Journey journey = new Journey();
        journey.setJourneyID(codPercorso);
        if (checkBox.isSelected()) {
            attivationTable.subscribeNotification(journey, person);
        } else {
            attivationTable.unsubscribeNotification(journey, person);
        }
    }

    @FXML
    public void cleanGridPane(GridPane gridPane) {
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
    public void populateGridPaneFromDatabase(List<JourneyInfo> journeyInfos) {
        int row = 1;
        for (JourneyInfo journeyInfo : journeyInfos) {
            Label labelPercorso = new Label(journeyInfo.getCodPercorso());
            Label labelBinario = new Label(String.valueOf(journeyInfo.getBinario()));
            Label labelOrarioPartenzaPrevisto = new Label(journeyInfo.getOrarioPartenzaPrevisto());
            Label labelStazionePartenza = new Label(journeyInfo.getStazionePartenzaNome());
            Label labelStazioneArrivo = new Label(journeyInfo.getStazioneArrivoNome());
            Label labelStatoArrivo = new Label(journeyInfo.getStatoArrivo());

            timetableGridPane.add(labelPercorso, 0, row);
            timetableGridPane.add(labelStazionePartenza, 1, row);
            timetableGridPane.add(labelStazioneArrivo, 2, row);
            timetableGridPane.add(labelOrarioPartenzaPrevisto, 3, row);
            timetableGridPane.add(labelBinario, 4, row);
            timetableGridPane.add(labelStatoArrivo, 5, row);

            row++;
        }
    }

    @FXML
    private void centerAllLabels(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
                GridPane.setValignment(node, VPos.CENTER);
                ((Label) node).setAlignment(Pos.CENTER);
            }
        }
    }

    @FXML
    private void setCheckBoxVisibleIfLabelHasText(GridPane gridPane, int labelCol, int row, int checkBoxCol) {
        Label label = (Label) getNodeFromGridPane(gridPane, labelCol, row);
        if (label != null && !label.getText().isEmpty()) {
            CheckBox checkBox = (CheckBox) getNodeFromGridPane(gridPane, checkBoxCol, row);
            checkBox.setVisible(true);
        }
    }

    @FXML
    private Object getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex == col && rowIndex == row) {
                return node;
            }
        }
        return null;
    }

    @FXML
    private void disableCheckBoxIfUserNotLoggedIn(GridPane gridPane, int row, int col, Controller controller) {
        CheckBox checkBox = (CheckBox) getNodeFromGridPane(gridPane, col, row);
        Person person = controller.getCurrentPerson().orElse(null);
        if (checkBox != null && person == null) {
                checkBox.setDisable(true);
        }
    }

    @FXML
    private void setCheckBoxSelected(GridPane gridPane, int row, int col, Controller controller) {
        CheckBox checkBox = (CheckBox) getNodeFromGridPane(gridPane, col, row);
        if (checkBox != null) {
            Label codPercorsoLabel = (Label) getNodeFromGridPane(gridPane, 0, row);
            if (codPercorsoLabel != null) {
                String codPercorso = codPercorsoLabel.getText();
                Person person = controller.getCurrentPerson().get();
                Journey journey = new Journey();
                journey.setJourneyID(codPercorso);
                checkBox.setSelected(attivationTable.isSubscribed(journey, person));
            }
        }
    }

    @FXML
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

}
