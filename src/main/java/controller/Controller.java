package controller;

import model.Model;
import model.Person;
import view.View;

public class Controller {
    private View view;
    private final Model model;
    private Person person;

    public Controller() {
        this.model = new Model();
    }

    public void initializeView(View view) {
        this.view = view;
        this.view.switchScene("home.fxml");
    }

    public void savePerson(Person person) {
        this.person = person;
    }

    public void goToTheNextDataScene(String path) {
        this.view.switchScene(path);
    }

    public Person getCurrentPerson() {
        return person;
    }
}
