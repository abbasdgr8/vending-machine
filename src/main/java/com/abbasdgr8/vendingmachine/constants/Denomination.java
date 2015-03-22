package com.abbasdgr8.vendingmachine.constants;

import java.util.Map;
import java.util.TreeMap;

import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import java.util.Set;

/**
 * <pre>
 * An enum class that holds the list of possible {@link com.abbasdgr8.vendingmachine.model.Coin}
 * denominations that the vending machine deals with.
 * The first argument is the value that this denomination holds.
 * The second argument is the local denomination name.
 * </pre>
 *
 * @author Abbas Attarwala
 */
public enum Denomination {
    
    ONE_POUND(100, "One pound"), 
    FIFTY_PENCE(50, "Fifty pence"), 
    TWENTY_PENCE(20, "Twenty pence"), 
    TEN_PENCE(10, "Ten pence"), 
    FIVE_PENCE(5, "Five pence"), 
    TWO_PENCE(2, "Two pence"), 
    ONE_PENNY(1, "One penny");
    
    
    /**
     * A static map that holds a reverse-lookup between the denominationValue and the {@link com.abbasdgr8.vendingmachine.constants.Denomination}
     * We use a {@link java.util.TreeMap} here to obtain a Map of ascending order of denominations.
     */
    public static final Map<Integer, Denomination> denominationLookup = new TreeMap<>();
    // A static block that builds this reverse-lookup map when this enum class is loaded.
    static {
        for (Denomination currentDenomination : Denomination.values()) {
            denominationLookup.put(currentDenomination.getDenominationValue(), currentDenomination);
        }
    }
    
    /**
     * A null-safe map-reader for the 
     * {@link com.abbasdgr8.vendingmachine.constants.Denomination#denominationLookup} map.
     * 
     * @param coinValue
     * @return 
     * @throws com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException 
     */
    public static Denomination get(int coinValue) throws NoSuchDenominationException {
        Denomination correspondingDenomination = denominationLookup.get(coinValue);
        if (correspondingDenomination == null) {
            throw new NoSuchDenominationException("A denomination of value-"
                    + coinValue + "does not exist.");
        }
        
        return correspondingDenomination;
    }
    
    
    private final int denominationValue;
    private final String denominationName;
    
    /**
     * Returns the coin value associated with the denomination.
     * 
     * @return denominationValue
     */
    public int getDenominationValue() {
        return denominationValue;
    }

    /**
     * Returns the local name associated with the denomination.
     * 
     * @return denominationName
     */
    public String getDenominationName() {
        return denominationName;
    }
    
    /**
     * The private constructor
     */
    private Denomination(int denominationValue, String denominationName) {
        this.denominationValue = denominationValue;
        this.denominationName = denominationName;
    }
}
