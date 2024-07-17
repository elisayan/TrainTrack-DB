package controller;

import model.Model;
import model.Person;
import model.Subscription;
import view.View;
import java.util.*;

public class Controller {
    private View view;
    private final Model model;
    private Person person;
    private List<Subscription> subscriptions;

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

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getCurrentPerson() {
        return person;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
