package controller;

import model.Model;
import model.Person;
import model.Subscription;
import model.Station;
import view.View;
import java.util.*;

import java.util.Optional;

public class Controller {
    private View view;
    private final Model model;
    private Person person;
    private List<Subscription> subscriptions;
    private Station station;

    public Controller() {
        this.model = new Model();
    }

    public void initializeView(View view) {
        this.view = view;
        this.view.switchScene("home.fxml");
    }

    public void goToTheNextDataScene(String path) {
        this.view.switchScene(path);
    }

    public void setPerson(Person person) {
        model.setPerson(person);
        this.person = person;
    }

    public Optional<Person> getCurrentPerson() {
        return Optional.ofNullable(this.person);
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getCurrentStation() {
        return station;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
