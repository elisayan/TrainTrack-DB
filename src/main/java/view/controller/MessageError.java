package view.controller;

public enum MessageError {

    EMPTY_FIELD("Please fill in the required fields"),

    ALREADY_EXIST("User already exist"),

    INCORRECT_INPUT("Email or Password incorrect"),

    USER_NOT_EXIST("User doesn't exist"),

    STATION_NOT_EXIST("Station doesn't exist"),

    TICKET_NOT_EXIST("Ticket doesn't exist"),

    INSERT_INFO("Insert your personal information"),

    VOUCHER_NOT_VALID("Voucher not valid"),

    ERROR("Error"),

    PERSON_NOT_EXIST("Person doesn't exist"),

    INSERT_MAIL("Please enter your email address"),

    SUGGESTION_EMAIL("Enter your email to view and check in your Tickets"),

    CHECKIN_NOT_IN_TIME("Check in is not valid in time"),

    CHECKIN_SUCCESSFUL("Check in successful");

    private final String message;

    MessageError(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "*" + this.message;
    }
}
