package view.controller;

import view.View;

public abstract class AbstractSceneController implements SceneController{
    protected View view;

    public void initialize (View view) {
        this.view = view;
    }
}
