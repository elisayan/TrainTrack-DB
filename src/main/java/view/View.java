package view;

import view.controller.SceneController;

import java.util.Optional;

public interface View {
    Optional<SceneController> switchScene(final String path);
}
