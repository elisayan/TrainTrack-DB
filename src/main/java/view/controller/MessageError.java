package view.controller;

public enum MessageError {
    EMPTY_FIELD("Please fill in the required fields"),

    ALREADY_EXIST("User already exist"),

    INCORRECT_INPUT("Email or Password incorrect"),

    USER_NOT_EXIST("User doesn't exist"),

    SUBSCRIPTION_NOT_EXIST("Subscription doesn't exist"),

    VOUCHER_NOT_EXIST("Invalid or used voucher"),

    PHONE_NOT_EXIST("Invalid phone number format"),

    ERROR("Error");

    private String message;

    MessageError(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "*" + this.message;
    }
}
