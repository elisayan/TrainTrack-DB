package controller;

import db.PersonTable;
import model.Person;
import view.controller.LoginSceneController;

public class LoginController {
    private final LoginSceneController view;
    private final PersonTable model;
    private final Controller controller;

    public LoginController(LoginSceneController view, Controller controller) {
        this.view = view;
        this.model = new PersonTable();
        this.controller = controller;
    }

    public void loginPerson(final String email, final String password) {
        Person currentPerson = new Person();
        if (this.model.loginUser(email, password)) {
            currentPerson.setEmail(email);
            currentPerson.setPassword(password);
            this.controller.setPerson(currentPerson);
            this.view.goForward(model.findPerson(email).get());
        } else if (model.findPerson(email).isEmpty()) {
            this.view.userNotExist();
        } else {
            this.view.loginFailed();
        }
    }
}
