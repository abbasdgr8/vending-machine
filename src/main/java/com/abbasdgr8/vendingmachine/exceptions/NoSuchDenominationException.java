package com.abbasdgr8.vendingmachine.exceptions;

/**
 * This exception is/will be/should be thrown when a coin value is specified 
 * for which the denomination does not exist.
 *
 * @author Abbas Attarwala
 */
public class NoSuchDenominationException extends Exception {

    /**
     * Constructor with message arg
     * 
     * @param message 
     */
    public NoSuchDenominationException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause args.
     * 
     * @param message
     * @param cause 
     */
    public NoSuchDenominationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
}
