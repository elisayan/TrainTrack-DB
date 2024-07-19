package view.controller;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.Subscription;
import view.ViewImpl;

import java.util.*;

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

    private Controller controller;
    private List<List<Subscription>> subscriptionGroups;

    @FXML
    public void initialize(ViewImpl view, Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    @FXML
    private void loginClicked() {
        this.view.switchScene("login.fxml");
    }

    public void showError(String message) {
        searchLabel.setText(message);
    }

    public void populateSearchTableSubs(List<List<Subscription>> subscriptionGroups) {
        this.subscriptionGroups = subscriptionGroups;
        ObservableList<Subscription> data = FXCollections.observableArrayList();
        
        if (subscriptionGroups != null) {
            for (List<Subscription> group : subscriptionGroups) {
                if (!group.isEmpty()) {
                    data.add(group.get(0)); // Aggiungi solo il primo elemento del gruppo
                }
            }
        } else {
            showError(MessageError.SUBSCRIPTION_NOT_EXIST.toString());
        }

        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureStation"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationStation"));
        beginningDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        searchTable.setItems(data);
        System.out.println("Data added to table: " + data);

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

    private void handleRowDoubleClick(Subscription selectedSubscription) {
        if (subscriptionGroups != null) {
            List<Subscription> selectedGroup = findGroupContainingSubscription(selectedSubscription);
            Optional<SceneController> optionalController = this.view.switchScene("subscriptionData.fxml");
            if (optionalController.isPresent()) {
                SubscriptionDataSceneController controller = (SubscriptionDataSceneController) optionalController.get();
                controller.setSubscriptionGroup(selectedGroup);
            }
        } else {
            showError(MessageError.SUBSCRIPTION_NOT_EXIST.toString());
        }
    }
    
    private List<Subscription> findGroupContainingSubscription(Subscription subscription) {
        if (subscriptionGroups != null) {
            for (List<Subscription> group : subscriptionGroups) {
                if (group.contains(subscription)) {
                    return group;
                }
            }
        }
        return Collections.emptyList();
    }
}
