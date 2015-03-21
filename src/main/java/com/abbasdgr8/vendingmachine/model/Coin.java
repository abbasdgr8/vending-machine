package com.abbasdgr8.vendingmachine.model;

import com.abbasdgr8.vendingmachine.constants.Denomination;
import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;

/**
 * Coin Model Object
 * 
 * This is an object-oriented representation of a real-life GBP-Sterling coin.
 *
 * @author Abbas Attarwala
 */
public final class Coin {
    
    /**
     * This is the value that the coin holds in GBP-Sterling
     */
    private final int denomination;
    
    /**
     * This is the name with which the coin is identified. In other words, 
     * this is the local name of the coin.
     */
    private final String name;

    /**
     * Constructor
     * 
     * @param denomination 
     * @throws com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException 
     */
    public Coin(int denomination) throws NoSuchDenominationException {
        this.denomination = denomination;
        this.name = Denomination.get(denomination).getCoinName();
    }
    
    /**
     * Returns the coin denomination
     * 
     * @return 
     */
    public int getDenomination() {
        return denomination;
    }

    /**
     * Returns the local denomination name.
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
}
