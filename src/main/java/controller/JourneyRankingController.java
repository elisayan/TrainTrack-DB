package controller;

import db.ThroughTable;
import model.DelayInfo;
import model.EarlyInfo;
import view.controller.JourneyRankingSceneController;

import java.util.*;

public class JourneyRankingController {
    private final JourneyRankingSceneController view;
    private final ThroughTable model;
    private final Controller controller;

    public JourneyRankingController(JourneyRankingSceneController view, Controller controller) {
        this.view = view;
        this.model = new ThroughTable();
        this.controller = controller;
    }

    public void updateDelayInfo() {
        List<DelayInfo> delayInfos = model.topFiveDelayJourney();
        view.populateDelayTable(delayInfos);
    }

    public void updateEarlyInfo() {
        List<EarlyInfo> earlyInfos = model.topFiveEarlyJourney();
        view.populateEarlyTable(earlyInfos);
    }
}
