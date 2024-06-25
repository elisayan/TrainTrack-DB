package controller;

import db.ThroughTable;
import view.controller.TimetableSceneController;

public class TimetableController {
    
    private final TimetableSceneController view;
    private final ThroughTable model;
    private final Controller controller;

    public TimetableController(TimetableSceneController view, Controller controller) {
        this.view = view;
        this.model = new ThroughTable();
        this.controller = controller;
    }

    
}
