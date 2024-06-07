package view.controller;

public enum MessageError {
    EMPTY_FIELD("Please fill in the required fields"),

    

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
