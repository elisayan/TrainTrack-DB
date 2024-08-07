package view.controller;

public enum Message {

    EMPTY_FIELD("Please fill in the required fields"),

    ALREADY_EXIST("User already exist"),

    INCORRECT_INPUT("Email or Password incorrect"),

    USER_NOT_EXIST("User doesn't exist"),

    SUBSCRIPTION_NOT_EXIST("Subscription doesn't exist"),

    VOUCHER_NOT_EXIST("Invalid or used voucher"),

    PHONE_NOT_EXIST("Invalid phone number format"),

    STATION_NOT_EXIST("Station doesn't exist"),

    TICKET_NOT_EXIST("There aren't tickets available for this journey"),

    INSERT_INFO("Insert your personal information"),

    VOUCHER_NOT_VALID("Voucher not valid"),

    ERROR("Error"),

    PERSON_NOT_EXIST("Person doesn't exist"),

    INSERT_MAIL("Please enter your email address"),

    SUGGESTION_EMAIL("Enter your email to view and check in your tickets"),

    CHECKIN_NOT_IN_TIME("Check-in is not valid in time"),

    CHECKIN_SUCCESSFUL("Check-in successful"),

    CHECKIN_ALREADY_DONE("Check-in has already been completed for this service");

    private final String message;

    Message(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "*" + this.message;
    }
}
