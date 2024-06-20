package controller;

import db.JourneyTable;
import db.StationTable;
import view.controller.TimetableSceneController;

public class TimetableController {
    
    private final TimetableSceneController view;
    private final JourneyTable model;
    private final Controller controller;

    public TimetableController(TimetableSceneController view, Controller controller) {
        this.view = view;
        this.model = new JourneyTable();
        this.controller = controller;
    }

    
}
