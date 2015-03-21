package com.abbasdgr8.vendingmachine.constants;

import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * An enum class that holds the list of possible {@link com.abbasdgr8.vendingmachine.model.Coin}
 * denominations that the vending machine is capable of dealing with.
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
     * A static map that holds a reverse-lookup between the coinValue and the denomination enum.
     */
    // Use treemap
    private static final Map<Integer, Denomination> denominationLookup = new HashMap<>();
    // A static block that builds this reverse-lookup map when this enum class is loaded.
    static {
        for (Denomination d : Denomination.values()) {
            denominationLookup.put(d.getCoinValue(), d);
        }
    }
    
    /**
     * This method will return the {@link com.abbasdgr8.vendingmachine.constants.Denomination} 
     * from the value of the coin specified.
     * 
     * @param coinValue
     * @return 
     * @throws com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException 
     */
    public static Denomination get(int coinValue) throws NoSuchDenominationException {
        Denomination correspondingDenomination = denominationLookup.get(coinValue);
        if (correspondingDenomination == null) {
            throw new NoSuchDenominationException("A denomination for coinValue-"
                    + coinValue + "does not exist.");
        }
        
        return correspondingDenomination;
    }
    
    
    private final int coinValue;
    private final String coinName;
    
    /**
     * Returns the coin value associated with the denomination.
     * 
     * @return coinValue
     */
    public int getCoinValue() {
        return coinValue;
    }

    /**
     * Returns the local name associated with the denomination.
     * 
     * @return coinName
     */
    public String getCoinName() {
        return coinName;
    }
    
    /**
     * The private constructor
     */
    private Denomination(int coinValue, String coinName) {
        this.coinValue = coinValue;
        this.coinName = coinName;
    }
}
