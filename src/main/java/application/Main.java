package application;

import javafx.application.Application;
import view.ViewImpl;

public final class Main{

    public static void main (final String[] args) {
        Application.launch(ViewImpl.class);
    }
}
