package view.controller;

import controller.Controller;
import controller.SubscriptionDataController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.Subscription;
import view.View;
import java.util.*;
import java.io.IOException;


public class SubscriptionSearchSceneController extends AbstractSceneController {

    @FXML
    private TableColumn<Subscription, String> beginningDateColumn;

    @FXML
    private TableColumn<Subscription, String> departureColumn;

    @FXML
    private TableColumn<Subscription, String> destinationColumn;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private TableColumn<Subscription, String> priceColumn;

    @FXML
    private TableView<Subscription> searchTable;

    @FXML
    private Label searchLabel;

    @FXML
    private TableColumn<Subscription, String> timeColumn;

    @FXML
    private TableColumn<Subscription, String> typeColumn;

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    public void showError(String message) {
        searchLabel.setText(message);
    }


    public void populateSearchTable(List<Subscription> subscriptions) {
        ObservableList<Subscription> data = FXCollections.observableArrayList(subscriptions);

        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureStation"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationStation"));
        beginningDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        searchTable.setItems(data);

        centerTableColumn(departureColumn);
        centerTableColumn(destinationColumn);
        centerTableColumn(beginningDateColumn);
        centerTableColumn(timeColumn);
        centerTableColumn(priceColumn);
        centerTableColumn(typeColumn);

        searchTable.setRowFactory(tv -> {
            TableRow<Subscription> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!row.isEmpty())) {
                    Subscription rowData = row.getItem();
                    handleRowDoubleClick(rowData);
                }
            });
            return row;
        });
    }

    private <S, T> void centerTableColumn(TableColumn<S, T> column) {
        column.setCellFactory(tc -> new TableCell<S, T>() {
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

    private void handleRowDoubleClick(Subscription subscription) {
        this.view.switchScene("subscriptionData.fxml");
    }

}
