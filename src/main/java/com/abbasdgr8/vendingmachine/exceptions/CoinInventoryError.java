package com.abbasdgr8.vendingmachine.exceptions;

/**
 * This error is thrown when there is a problem while reading from OR writing to
 * the coin inventory
 *
 * @author Abbas Attarwala
 */
public class CoinInventoryError extends Exception {
    
    /**
     * Constructor with message arg
     * 
     * @param message 
     */
    public CoinInventoryError(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause args.
     * 
     * @param message
     * @param cause 
     */
    public CoinInventoryError(String message, Throwable cause) {
        super(message, cause);
    }
    
}
