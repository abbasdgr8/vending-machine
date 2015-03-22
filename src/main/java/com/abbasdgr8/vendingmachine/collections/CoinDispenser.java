package com.abbasdgr8.vendingmachine.collections;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import com.abbasdgr8.vendingmachine.model.Coin;

/**
 * This is a real-life vending machine coin dispenser that has been implemented 
 * from a {@link java.util.concurrent.ArrayBlockingQueue}
 * This has also been implemented as a singleton class.
 *
 * @author Abbas Attarwala
 */
public final class CoinDispenser extends ArrayBlockingQueue<Coin> {
    
    private static CoinDispenser theCoinDispenser = new CoinDispenser(100);
    private final CoinBank coinBank = CoinBank.getInstance();
    
    /**
     * A static 'instance' method that returns the same instance of {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * each time the method is called.
     * 
     * @return 
     */
    public static CoinDispenser getInstance() {
        return theCoinDispenser;
    }
    
    public synchronized Collection<Coin> releaseCoins(int coinDenomination, int numberOfCoinsToRelease) 
                                            throws NoSuchDenominationException {
        
        Collection<Coin> coinsReleased = coinBank.withdraw(coinDenomination, numberOfCoinsToRelease);
        
        return coinsReleased;
    }

    /**
     * The private constructor
     */
    private CoinDispenser(int capacity) {
        super(capacity);
        coinBank.setUnlimitedSupplyOfCoins(true);
    }
   
    /**
     * A repository where the vending machine stores its coins. This is
     * implemented as a singleton class and returns the same instance of the
     * bank each time {@link com.abbasdgr8.vendingmachine.repository.CoinBank#getInstance()
     * }
     * is called.
     *
     * @author Abbas Attarwala
     */
    private static class CoinBank {

        private boolean unlimitedSupplyOfCoins;
        private static final CoinBank theCoinBank = new CoinBank();

        /**
         * A static 'instance' method that returns the same instance of
         * {@link com.abbasdgr8.vendingmachine.repository.CoinBank} each time
         * the method is called.
         *
         * @return
         */
        public static CoinBank getInstance() {
            return theCoinBank;
        }

        public synchronized Collection<Coin> withdraw(int coinDenomination, int numberOfCoinsToRelease) throws NoSuchDenominationException {
            if (unlimitedSupplyOfCoins) {
                for (int i = 0; i < numberOfCoinsToRelease; i++) {
                    Coin coin = new Coin(coinDenomination);
                    theCoinDispenser.offer(coin);
                }
            } else {
                // get coins from bank
            }

            return theCoinDispenser;
        }

        public synchronized boolean deposit(Collection<Coin> coinsToDeposit) {
            return false;
        }

        /**
         * Getter for setting whether the coinBank has an unlimited Supply Of
         * Coins or not.
         *
         * @param unlimitedSupplyOfCoins
         */
        public void setUnlimitedSupplyOfCoins(boolean unlimitedSupplyOfCoins) {
            this.unlimitedSupplyOfCoins = unlimitedSupplyOfCoins;
        }

        /**
         * The private constructor
         */
        private CoinBank() {
        }
    }
    
}
