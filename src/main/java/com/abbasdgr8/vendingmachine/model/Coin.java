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
        this.name = Denomination.get(denomination).getDenominationName();
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
    
    /**
     * The over-ridden toString implementation
     * 
     * @return 
     */
    @Override
    public String toString() {
        return this.getName();
    }
    
    /**
     * The over-ridden hashCode implementation
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.denomination;
        return hash;
    }

    /**
     * The over-ridden equals implementation
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coin other = (Coin) obj;
        if (this.denomination != other.denomination) {
            return false;
        }
        return true;
    }
    
}
