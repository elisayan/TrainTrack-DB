package controller;

import java.util.List;

import db.PersonTable;
import model.Person;
import view.controller.ExpenseRankingSceneController;

public class ExpenseRankingController {
    private final ExpenseRankingSceneController view;
    private final PersonTable model;
    private final Controller controller;

    public ExpenseRankingController(ExpenseRankingSceneController view, Controller controller) {
        this.view = view;
        this.model = new PersonTable();
        this.controller = controller;
    }

    public void updateSpendersInfo(){
        List<Person> personTable = model.getTopFiveSpenders();
        view.populateExpensesTable(personTable);
    }
}
