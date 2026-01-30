package org.example;

public class AlreadyRegisteredUserException extends Exception {

    public AlreadyRegisteredUserException(String message) {
        super(message);
    }
}
