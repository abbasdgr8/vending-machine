package com.abbasdgr8.vendingmachine;

import java.util.Collection;
import java.util.Set;

import com.abbasdgr8.vendingmachine.collections.CoinDispenser;
import com.abbasdgr8.vendingmachine.constants.Denomination;
import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import com.abbasdgr8.vendingmachine.model.Coin;
import com.abbasdgr8.vendingmachine.repository.CoinBank;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *
 * @author Abbas Attarwala
 */
public class VendingMachine {
    
    private final CoinBank coinBank = CoinBank.getInstance();
    private final CoinDispenser coinDispenser = CoinDispenser.getInstance();
    
    public Collection<Coin> getOptimalChangeFor(int pence) throws NoSuchDenominationException {
        
        coinBank.setUnlimitedSupplyOfCoins(true);
        
        int remainingChange = pence;
        Set<Integer> denominationKeySet = Denomination.denominationLookup.keySet();
        Integer[] sortedDenominationArray = denominationKeySet.toArray(new Integer[denominationKeySet.size()]);
        
        for(int i = sortedDenominationArray.length - 1; i > 0; i--) {
            int currentDenominationValue = Denomination.get(sortedDenominationArray[i]).getDenominationValue();
            if (remainingChange >= currentDenominationValue) {
                int currentDenominationCoinCount = remainingChange / currentDenominationValue;
                coinBank.releaseCoins(currentDenominationValue, currentDenominationCoinCount);
                remainingChange = remainingChange - currentDenominationCoinCount * currentDenominationValue;
            }
        }
        
        return coinDispenser;
    }
    
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        Collection<Coin> coins = null;
        try {
            coins = vendingMachine.getOptimalChangeFor(49);
        } catch (NoSuchDenominationException ex) {
            Logger.getLogger(VendingMachine.class.getName()).log(Level.SEVERE, "Something went wrong", ex);
        }
        
        System.out.println("Output >>>>>> " + coins.size());
    }
    
}
