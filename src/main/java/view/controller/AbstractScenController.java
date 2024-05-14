package view.controller;

import view.View;

public abstract class AbstractScenController implements SceneController{
    protected View view;

    public void initialize (View view) {
        this.view = view;
    }
}
