package controller;

import db.PersonTable;
import view.controller.LoginSceneController;

public class LoginController {
    private final LoginSceneController view;
    private final PersonTable model;

    public LoginController(LoginSceneController view) {
        this.view = view;
        this.model = new PersonTable();
    }
    
    public void loginPerson(final String email, final String password){
        if (this.model.loginUser(email, password)) {
            this.view.goForeward(this.model.getCurrentPerson());
        } else{
            this.view.loginFailed();
        }
    }
}
