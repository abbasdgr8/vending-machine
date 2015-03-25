package com.abbasdgr8.vendingmachine;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abbasdgr8.vendingmachine.collections.CoinDispenser;
import com.abbasdgr8.vendingmachine.constants.Denomination;
import com.abbasdgr8.vendingmachine.exceptions.CoinInventoryError;
import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import com.abbasdgr8.vendingmachine.model.Coin;


/**
 * An implementation of a vending machine with coin vending functionalities like a 
 * real-life vending-machine.
 *
 * @author Abbas Attarwala
 */
public class VendingMachine {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VendingMachine.class);
    
    /**
     * This method assumes an unlimited supply of coins while evaluating 
     * {@link com.abbasdgr8.vendingmachine.model.Coin}s to return for change.
     * It instructs the {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * to {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser#getCoins(int, int)}
     * from the CoinBank.
     * It starts with coins of the highest denomination and dispenses change 
     * after which it comes down to lower denominations of coins until all the 
     * remaining change has been dispensed. This ensures that least number of coins are dispensed.
     * 
     * @param pence
     * @return
     * @throws NoSuchDenominationException
     * @throws CoinInventoryError 
     */
    public Collection<Coin> getOptimalChangeFor(int pence) throws NoSuchDenominationException, 
                                                                  CoinInventoryError {
        
        CoinDispenser coinDispenser = CoinDispenser.getInstance();
        coinDispenser.setUnlimitedSupplyOfCoins(true);
        
        pence = (pence < 0) ? 0 : pence;
        int remainingChange = pence;
        
        Set<Integer> denominationKeySet = Denomination.denominationLookup.keySet();
        Integer[] sortedDenominationArray = denominationKeySet.toArray(new Integer[denominationKeySet.size()]);
        
        for(int i = sortedDenominationArray.length - 1; i >= 0; i--) {
            int currentDenominationValue = Denomination.get(sortedDenominationArray[i]).getDenominationValue();
            if (remainingChange >= currentDenominationValue) {
                int currentDenominationCoinCount = remainingChange / currentDenominationValue;
                if (coinDispenser.getCoins(currentDenominationValue, currentDenominationCoinCount))
                remainingChange = remainingChange - currentDenominationCoinCount * currentDenominationValue;
            }
        }
        
        return coinDispenser;
    }
    
    /**
     * This method deducts coins from an inventory while evaluating 
     * {@link com.abbasdgr8.vendingmachine.model.Coin}s to return for change.
     * It instructs the {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * to {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser#getCoins(int, int)}
     * from the CoinBank.
     * It starts with coins of the highest denomination and dispenses change 
     * after which it comes down to lower denominations of coins until all the 
     * remaining change has been dispensed. This ensures that least number of coins are dispensed.
     * 
     * @param pence
     * @return
     * @throws NoSuchDenominationException
     * @throws CoinInventoryError 
     */
    public Collection<Coin> getChangeFor(int pence) throws NoSuchDenominationException, CoinInventoryError {
        
        CoinDispenser coinDispenser = CoinDispenser.getInstance();
        coinDispenser.setUnlimitedSupplyOfCoins(false);
        
        pence = (pence < 0) ? 0 : pence;
        int remainingChange = pence;
        
        Set<Integer> denominationKeySet = Denomination.denominationLookup.keySet();
        Integer[] sortedDenominationArray = denominationKeySet.toArray(new Integer[denominationKeySet.size()]);
        
        for(int i = sortedDenominationArray.length - 1; i >= 0; i--) {
            int currentDenominationValue = Denomination.get(sortedDenominationArray[i]).getDenominationValue();
            if (remainingChange >= currentDenominationValue) {
                int currentDenominationCoinCount = remainingChange / currentDenominationValue;
                if (coinDispenser.getCoins(currentDenominationValue, currentDenominationCoinCount)) {
                    remainingChange = remainingChange - currentDenominationCoinCount * currentDenominationValue;
                }
            }
        }
        
        return coinDispenser;
    }

    /**
     * This empties/clears the {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * off the current lot of {@link com.abbasdgr8.vendingmachine.model.Coin}s
     */
    public void emptyCoinDispenser() {
        CoinDispenser coinDispenser = CoinDispenser.getInstance();
        coinDispenser.clear();
    }
    
}
