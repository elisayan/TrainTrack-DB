package view.controller;

//import com.mysql.cj.xdevapi.Statement;

import controller.Controller;
import controller.TimetableController;
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

    private final DBConnection dataSource = new DBConnection();

    @Override
    public void initialize(View view, Controller controller) {
        super.initialize(view, controller);
        this.controller = new TimetableController(this, this.getController());
    }

    @FXML
    public void searchClicked() throws SQLException {
    String station = stationField.getText().trim();

    final int rows = 9;
    final int columns = 7;

    
    cleanGridPane(timetableGridPane);
    cleanLabel(errorLabel);

    if (!station.isBlank() && isFieldsExist(station)) {
        // Recupero dei dati e aggiunta al GridPane
        populateGridPaneFromDatabase(timetableGridPane, station);
        centerAllLabels(timetableGridPane);

        for (int i = 1; i < rows; i++) {
            addCheckBoxIfLabelHasText(timetableGridPane, 0, i, columns - 1);
        }
    } else {
        errorLabel.setText(MessageError.STATION_NOT_EXIST.toString());
        }
    }

@FXML
public void cleanGridPane(GridPane gridPane) {
    // Crea una lista per memorizzare i nodi da rimuovere
    List<Node> nodesToRemove = new ArrayList<>();

    // Itera su ogni nodo nel GridPane
    for (Node node : gridPane.getChildren()) {
        // Ottiene l'indice della riga del nodo
        Integer rowIndex = GridPane.getRowIndex(node);
        // Se la riga è null, significa che è la prima riga (indice 0)
        if (rowIndex == null) {
            rowIndex = 0;
        }
        // Se la riga è maggiore di 0, aggiungi il nodo alla lista da rimuovere
        if (rowIndex > 0) {
            nodesToRemove.add(node);
        }
    }

    // Rimuove i nodi memorizzati nella lista dal GridPane
    gridPane.getChildren().removeAll(nodesToRemove);
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
        String selectPercorsoQuery = "SELECT a.CodPercorso, a.Binario, a.OrarioPartenzaPrevisto, a.StatoArrivo " + 
                                     "FROM attraversato a " + 
                                     "JOIN stazione s ON s.CodStazione = a.CodStazione " + 
                                     "WHERE s.Nome = ?";
        String selectStazionePartenzaQuery = "SELECT s.Nome " +
                                             "FROM stazione s " +
                                             "JOIN attraversato a ON s.CodStazione = a.CodStazione " +
                                             "WHERE a.Ordine = '1' AND a.CodPercorso = (" + 
                                                "SELECT at.CodPercorso " + 
                                                "FROM attraversato at " + 
                                                "JOIN stazione s ON s.CodStazione = at.CodStazione " + 
                                                "WHERE s.Nome = ?)";
        String selectStazioneArrivoQuery = "SELECT s.Nome " +
                                           "FROM stazione s " +
                                           "JOIN attraversato a ON s.CodStazione = a.CodStazione " +
                                           "WHERE Ordine = (SELECT MAX(Ordine) FROM attraversato aa JOIN percorso p ON p.CodPercorso = aa.CodPercorso) " + 
                                           "AND a.CodPercorso = ( " + 
                                                "SELECT at.CodPercorso " + 
                                                "FROM attraversato at " + 
                                                "JOIN stazione s ON s.CodStazione = at.CodStazione " + 
                                                "WHERE s.Nome = ?)";


        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPercorsoQuery)) {
            
            // Impostazione del parametro della query
            pstmt.setString(1, station);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                int row = 1;
                while (rs.next()) {
                    String codPercorso = rs.getString("CodPercorso");
                    int binario = rs.getInt("Binario");
                    String orarioPartenzaPrevisto = rs.getString("OrarioPartenzaPrevisto");
                    String statoArrivo = rs.getString("StatoArrivo");
    
                    Label labelPercorso = new Label(codPercorso);
                    Label labelBinario = new Label(String.valueOf(binario));
                    Label labelOrarioPartenzaPrevisto = new Label(orarioPartenzaPrevisto);
                    Label labelStatoArrivo = new Label(statoArrivo);
    
                    gridPane.add(labelPercorso, 0, row);
                    gridPane.add(labelBinario, 4, row);
                    gridPane.add(labelOrarioPartenzaPrevisto, 3, row);
                    gridPane.add(labelStatoArrivo, 5, row);
    
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectStazioneArrivoQuery)) {
            
            // Impostazione del parametro della query
            pstmt.setString(1, station);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                int row = 1;
                while (rs.next()) {
                    String nomeStazioneArrivo = rs.getString("Nome");
                
                    Label labelStazioneArrivo = new Label(nomeStazioneArrivo);
                
                    gridPane.add(labelStazioneArrivo, 2, row); 

                    row++;
                }
            } catch (Exception e) {
            e.printStackTrace();
            }
        }

        try (Connection conn = dataSource.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectStazionePartenzaQuery)) {
            
            // Impostazione del parametro della query
            pstmt.setString(1, station);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                int row = 1;
                while (rs.next()) {
                    String nomeStazionePartenza = rs.getString("Nome");
                
                    Label labelStazionePartenza = new Label(nomeStazionePartenza);
                
                    gridPane.add(labelStazionePartenza, 1, row); 

                    row++;
                }
            } catch (Exception e) {
            e.printStackTrace();
            }
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
    public void loginClicked() {
        this.view.switchScene("login.fxml");
    }

}
