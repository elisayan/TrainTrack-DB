package controller;

import java.util.List;

import db.PersonTable;
import model.Person;
import view.controller.ExpenseRankingSceneController;

public class ExpenseRankingController {
    private final ExpenseRankingSceneController view;
    private final PersonTable model;

    public ExpenseRankingController(ExpenseRankingSceneController view) {
        this.view = view;
        this.model = new PersonTable();
    }

    public void updateSpendersInfo(){
        List<Person> personTable = model.getTopFiveSpenders();
        view.populateExpensesTable(personTable);
    }
}
