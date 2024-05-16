package controller;

import model.Model;
import model.Person;
import view.View;

public class Controller {
    private View view;
    private final Model model;

    public Controller() {
        this.model = new Model();
    }

    public void initializeView(View view){
        this.view = view;
        this.view.switchScene("home.fxml");
    }

    public void savePerson(Person person){
        this.model.setUser(person);
    }

    
}
