package view.controller;

import model.DelayInfo;
import model.EarlyInfo;

import java.util.*;

import controller.JourneyRankingController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

public class JourneyRankingSceneController extends AbstractSceneController {

    @FXML
    private TableColumn<DelayInfo, String> delayDestinationColumn;

    @FXML
    private TableColumn<DelayInfo, Float> delayAverageColumn;

    @FXML
    private TableColumn<DelayInfo, String> delayDepartureColumn;

    @FXML
    private TableColumn<DelayInfo, String> delayJourneyIDColumn;

    @FXML
    private TableColumn<DelayInfo, Integer> delayRankingColumn;

    @FXML
    private TableView<DelayInfo> delayTable;

    @FXML
    private TableColumn<EarlyInfo, String> earlyDestinationColumn;

    @FXML
    private TableColumn<EarlyInfo, Float> earlyAverageColumn;

    @FXML
    private TableColumn<EarlyInfo, String> earlyDepartureColumn;

    @FXML
    private TableColumn<EarlyInfo, String> earlyJourneyIDColumn;

    @FXML
    private TableColumn<EarlyInfo, Integer> earlyRankingColumn;

    @FXML
    private TableView<EarlyInfo> earlyTable;

    private final JourneyRankingController controller= new JourneyRankingController(this);

    @FXML
    public void initialize() {
        this.delayJourneyIDColumn.setCellValueFactory(new PropertyValueFactory<>("codPercorso"));
        this.delayDepartureColumn.setCellValueFactory(new PropertyValueFactory<>("stazionePartenzaNome"));
        this.delayDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("stazioneDestinazioneNome"));
        this.delayAverageColumn.setCellValueFactory(new PropertyValueFactory<>("mediaMinutiRitardo"));
        this.delayRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        this.earlyJourneyIDColumn.setCellValueFactory(new PropertyValueFactory<>("codPercorso"));
        this.earlyDepartureColumn.setCellValueFactory(new PropertyValueFactory<>("stazionePartenzaNome"));
        this.earlyDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("stazioneDestinazioneNome"));
        this.earlyAverageColumn.setCellValueFactory(new PropertyValueFactory<>("mediaMinutiAnticipo"));
        this.earlyRankingColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        centerTableColumn(delayJourneyIDColumn);
        centerTableColumn(delayDepartureColumn);
        centerTableColumn(delayDestinationColumn);
        centerTableColumn(delayRankingColumn);
        centerTableColumn(earlyJourneyIDColumn);
        centerTableColumn(earlyDepartureColumn);
        centerTableColumn(earlyDestinationColumn);
        centerTableColumn(earlyRankingColumn);

        formatAverageColumn(delayAverageColumn, true);
        formatAverageColumn(earlyAverageColumn, false);

        controller.updateDelayInfo();
        controller.updateEarlyInfo();
    }

    @FXML
    private void userClicked(){
        this.view.switchScene("login.fxml");
    }

    private <S, T> void centerTableColumn(TableColumn<S, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
    }

    private <S> void formatAverageColumn(TableColumn<S, Float> column, boolean isDelay) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    String formattedValue = isDelay ? String.format("+%.2f min", item) : String.format("-%.2f min", item);
                    setText(formattedValue);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
    }

    public void populateDelayTable(List<DelayInfo> delayInfos) {
        delayTable.getItems().setAll(delayInfos);
        for (int i = 0; i < delayInfos.size(); i++) {
            delayInfos.get(i).setRank(i + 1);
        }
    }

    public void populateEarlyTable(List<EarlyInfo> earlyInfos) {
        earlyTable.getItems().setAll(earlyInfos);
        for (int i = 0; i < earlyInfos.size(); i++) {
            earlyInfos.get(i).setRank(i + 1);
        }
    }
}
