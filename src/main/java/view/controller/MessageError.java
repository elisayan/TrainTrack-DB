package view.controller;

public enum MessageError {
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
