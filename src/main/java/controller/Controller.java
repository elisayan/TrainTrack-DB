package controller;

import model.Model;
import model.Person;
import model.Station;
import view.View;

public class Controller {
    private View view;
    private final Model model;
    private Person person;
    private Station station;

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

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getCurrentStation() {
        return station;
    }
}
