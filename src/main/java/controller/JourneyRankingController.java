package controller;

import db.ThroughTable;
import model.DelayInfo;
import model.EarlyInfo;
import view.controller.JourneyRankingSceneController;

import java.util.*;

public class JourneyRankingController {
    private final JourneyRankingSceneController view;
    private final ThroughTable model;

    public JourneyRankingController(JourneyRankingSceneController view) {
        this.view = view;
        this.model = new ThroughTable();
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
