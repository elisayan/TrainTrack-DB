package controller;

import db.PersonTable;
import view.controller.Login;

public class LoginController {
    private final Login view;
    private final PersonTable model;

    public LoginController(Login view, PersonTable model) {
        this.view = view;
        this.model = model;
    }
    
}
