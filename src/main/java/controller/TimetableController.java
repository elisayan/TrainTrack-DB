package controller;

import db.ThroughTable;
import model.JourneyInfo;
import model.Station;
import view.controller.Message;
import view.controller.TimetableSceneController;

import java.sql.SQLException;
import java.util.List;

public class TimetableController {
    
    private final TimetableSceneController view;
    private final ThroughTable model;
    private final Controller controller;

    public TimetableController(TimetableSceneController view, Controller controller) {
        this.view = view;
        this.model = new ThroughTable();
        this.controller = controller;
    }

    public void searchStation(final String station) {
        if (station == null || station.trim().isEmpty()) {
            this.view.showError(Message.EMPTY_FIELD.toString());
            return;
        }

        Station currentStation = new Station();
        currentStation.setStationName(station);

        if (isStationExist(currentStation)) {
            this.controller.setStation(currentStation);
            this.view.updateTimetableView(currentStation);
        } else {
            this.view.showError(Message.STATION_NOT_EXIST.toString());
        }
    }

    private boolean isStationExist(Station station) {
        try {
            return this.model.isStationExist(station);
        } catch (SQLException e) {
            e.printStackTrace();
            this.view.showError(Message.ERROR.toString());
            return false;
        }
    }

    public void updateJourneyInfo(Station station) throws SQLException {
        List<JourneyInfo> journeyInfo = model.journeyInfo(station);
        view.populateGridPaneFromDatabase(journeyInfo);
    }
}
