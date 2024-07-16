package controller;

import db.ServiceTable;
import view.controller.PassengerDetailSceneController;

import java.time.LocalTime;

public class PassengerDetailController {
    private final PassengerDetailSceneController view;
    private final ServiceTable model;

    public PassengerDetailController(PassengerDetailSceneController view) {
        this.view = view;
        this.model = new ServiceTable();
    }

    public void saveTicket(String journeyID, String departureStation, String destinationStation, LocalTime departureTime,
                           String typeTrain, float ticketPrice, String firstName, String lastName, String email,
                           String passengerID, String address, String phone, String voucher){
        //prendere da view le info di input inserite dall'utente per comprare biglietto
        //passare poi a model per salvare questo biglietto
        //inserire: codServizio, stazionePartenza, stazioneArrivo, nome, cognome, tipo treno, data partenza, codPercorso, email
    }
}
