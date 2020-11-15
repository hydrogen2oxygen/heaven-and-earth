package net.hydrogen2oxygen.heavenAndEarth.exceptions;

public class DatabaseInitializationException extends Exception {

    public DatabaseInitializationException(String message) {
        super(message);
    }

    public DatabaseInitializationException(String message, Throwable e) {
        super(message, e);
    }
}
