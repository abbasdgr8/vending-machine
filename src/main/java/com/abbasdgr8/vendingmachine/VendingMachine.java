package com.abbasdgr8.vendingmachine;

import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.abbasdgr8.vendingmachine.collections.CoinDispenser;
import com.abbasdgr8.vendingmachine.constants.Denomination;
import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import com.abbasdgr8.vendingmachine.model.Coin;

/**
 * 
 *
 * @author Abbas Attarwala
 */
public class VendingMachine {
    
    private final CoinDispenser coinDispenser = CoinDispenser.getInstance();
    
    public Collection<Coin> getOptimalChangeFor(int pence) throws NoSuchDenominationException {
        
        pence = (pence < 0) ? 0 : pence;
        int remainingChange = pence;
        
        Set<Integer> denominationKeySet = Denomination.denominationLookup.keySet();
        Integer[] sortedDenominationArray = denominationKeySet.toArray(new Integer[denominationKeySet.size()]);
        
        for(int i = sortedDenominationArray.length - 1; i >= 0; i--) {
            int currentDenominationValue = Denomination.get(sortedDenominationArray[i]).getDenominationValue();
            if (remainingChange >= currentDenominationValue) {
                int currentDenominationCoinCount = remainingChange / currentDenominationValue;
                coinDispenser.releaseCoins(currentDenominationValue, currentDenominationCoinCount);
                remainingChange = remainingChange - currentDenominationCoinCount * currentDenominationValue;
            }
        }
        
        return coinDispenser;
    }
    
    
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        Collection<Coin> coins = null;
        try {
            coins = vendingMachine.getOptimalChangeFor(738);
            System.out.println(coins.size() + " coins in dispenser.");
            Iterator<Coin> iterator = coins.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception ex) {
            Logger.getLogger(VendingMachine.class.getName()).log(Level.SEVERE, "Something went wrong", ex);
        }
    }
    
}
