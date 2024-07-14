package view;

import controller.Controller;

import java.io.*;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.controller.SceneController;

public class ViewImpl extends Application implements View {
    // private final SceneLoader sceneLoader;

    private final static String PATH = "/layouts/";
    private Stage stage;
    private Controller controller;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setHeight(Screen.getPrimary().getBounds().getHeight() / 2);
        this.stage.setWidth(Screen.getPrimary().getBounds().getWidth() / 2);
        this.stage.setTitle("TrainTrack");
        this.stage.getIcons().add(new Image("img/trainTrack_icon.png"));
        this.stage.setResizable(true);
        this.stage.show();
        this.controller = new Controller();
        this.controller.initializeView(this);
    }

    private Optional<SceneController> loadScene(final String filePath) {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + filePath));

        final Parent root;
        try {
            root = loader.load(this.getClass().getResourceAsStream(PATH + filePath));
        final SceneController sceneController = loader.getController();
        sceneController.setRoot(root);
        sceneController.initialize(this, this.controller);
        if (this.stage.getScene() == null) {
            this.stage.setScene(new Scene(root));
        } else {
            this.stage.getScene().setRoot(root);
        }
        return Optional.of(sceneController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<SceneController> switchScene(final String path) {
        // this.currentSceneController = this.sceneLoader.loadScene(path).get();
        // this.initializeSceneController(this.currentSceneController);
        return this.loadScene(path);
    }
}
