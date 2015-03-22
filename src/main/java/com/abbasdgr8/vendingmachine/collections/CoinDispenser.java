package com.abbasdgr8.vendingmachine.collections;

import com.abbasdgr8.vendingmachine.model.Coin;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is a real-life vending machine coin dispenser that has been implemented 
 * from a {@link java.util.concurrent.ArrayBlockingQueue}
 * This has also been implemented as a singleton class.
 *
 * @author Abbas Attarwala
 */
public final class CoinDispenser extends ArrayBlockingQueue<Coin> {
    
    private static CoinDispenser theCoinDispenser = new CoinDispenser(100);
    
    /**
     * A static 'instance' method that returns the same instance of {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * each time the method is called.
     * 
     * @return 
     */
    public static CoinDispenser getInstance() {
        return theCoinDispenser;
    }

    /**
     * The private constructor
     */
    private CoinDispenser(int capacity) {
        super(capacity);
    }
    
}
