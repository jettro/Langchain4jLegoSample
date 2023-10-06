package dev.jettro.csv;

/**
 * Exception used to indicate a problem while importing the CSV file.
 */
public class ParseCSVException extends RuntimeException {
    /**
     * Default constructor containing the message about the reason for the exception
     * @param message String containing the message describing the reason for the error
     */
    public ParseCSVException(String message) {
        super(message);
    }

}
