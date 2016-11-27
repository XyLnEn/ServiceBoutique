package com.alma.boutique.application.data;

/**
 * Data class used to represents an error from the API
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class Error {
    private String name;
    private String message;

    public Error() {
    }

    public Error(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
