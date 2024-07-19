package controller;

import db.PersonTable;
import view.controller.ExpenseRankingSceneController;

public class ExpenseRankingController {
    private final ExpenseRankingSceneController view;
    private final PersonTable model;

    public ExpenseRankingController(ExpenseRankingSceneController view) {
        this.view = view;
        this.model = new PersonTable();
    }

    public void updateSpendersInfo(){
        view.populateExpensesTable(model.getAllSpendersRanking());
    }
}
