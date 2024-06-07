package controller;

import db.PersonTable;
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
    
    public void loginPerson(final String email, final String password){
        if (this.model.loginUser(email, password)) {
            this.view.goForeward(controller.getCurrentPerson());
        } else{
            this.view.loginFailed();
        }
    }
}
