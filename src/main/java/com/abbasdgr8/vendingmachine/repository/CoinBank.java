package com.abbasdgr8.vendingmachine.repository;

import java.util.Collection;

import com.abbasdgr8.vendingmachine.collections.CoinDispenser;
import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import com.abbasdgr8.vendingmachine.model.Coin;

/**
 * A repository where the vending machine stores its coins.
 * This is implemented as a singleton class and returns the same instance of the 
 * bank each time {@link com.abbasdgr8.vendingmachine.repository.CoinBank#getInstance() }
 * is called.
 *
 * @author Abbas Attarwala
 */
public final class CoinBank {
    
    private final CoinDispenser coinDispenser = CoinDispenser.getInstance();
    private boolean unlimitedSupplyOfCoins;
    
    private static CoinBank theCoinBank = new CoinBank();
    
    /**
     * A static 'instance' method that returns the same instance of {@link com.abbasdgr8.vendingmachine.repository.CoinBank}
     * each time the method is called.
     * 
     * @return 
     */
    public static CoinBank getInstance() {
        return theCoinBank;
    }
    
    public synchronized Collection<Coin> releaseCoins(int coinDenomination, int numberOfCoinsToRelease) throws NoSuchDenominationException {
        if (unlimitedSupplyOfCoins) {
            for (int i = 0; i < numberOfCoinsToRelease; i++) {
                Coin coin = new Coin(coinDenomination);
                coinDispenser.offer(coin);
            }
        } else {
            // get coins from bank
        }
        
        return coinDispenser;
    }
    
    /**
     * Getter for setting whether the coinBank has an unlimited Supply Of Coins or not.
     * 
     * @param unlimitedSupplyOfCoins 
     */
    public void setUnlimitedSupplyOfCoins(boolean unlimitedSupplyOfCoins) {
        this.unlimitedSupplyOfCoins = unlimitedSupplyOfCoins;
    }
    
    /**
     * The private constructor
     */
    private CoinBank() { }
}
