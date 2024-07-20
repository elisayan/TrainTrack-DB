package controller;

import db.PersonTable;
import model.Person;
import view.controller.SignUpSceneController;

public class SignUpController {
    private final SignUpSceneController view;
    private final PersonTable model;

    public SignUpController(SignUpSceneController view) {
        this.view = view;
        this.model = new PersonTable();
    }

    public void signUpPerson(final Person person) {
        if (this.model.isGuest(person)){
            this.model.updateToUser(person);
            this.view.goForward(person);
        } else if (this.model.signUpPerson(person)) {
            this.view.goForward(person);
        } else {
            this.view.singUpFailed();
        }
    }
}
