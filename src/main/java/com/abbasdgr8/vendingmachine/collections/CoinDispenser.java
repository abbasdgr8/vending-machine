package com.abbasdgr8.vendingmachine.collections;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import com.abbasdgr8.vendingmachine.exceptions.NoSuchDenominationException;
import com.abbasdgr8.vendingmachine.model.Coin;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

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
    
    public synchronized boolean releaseCoins(int coinDenomination, int numberOfCoinsToRelease) 
                                            throws NoSuchDenominationException, ConfigurationException {
        
        return coinBank.withdraw(coinDenomination, numberOfCoinsToRelease);
    }
    
    /**
     * Set whether the coinBank should have an unlimited Supply Of Coins or not.
     *
     * @param unlimitedSupplyOfCoins
     */
    public void setUnlimitedSupplyOfCoins(boolean unlimitedSupplyOfCoins) {
        coinBank.setUnlimitedSupplyOfCoins(unlimitedSupplyOfCoins);
    }

    /**
     * The private constructor
     */
    private CoinDispenser(int capacity) {
        super(capacity);
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

        public synchronized boolean withdraw(int coinDenomination, int numberOfCoinsToRelease) throws NoSuchDenominationException, ConfigurationException {
            boolean withdrawSuccess = false;
            
            if (unlimitedSupplyOfCoins) {
                for (int i = 0; i < numberOfCoinsToRelease; i++) {
                    Coin coin = new Coin(coinDenomination);
                    theCoinDispenser.offer(coin);
                    withdrawSuccess = true;
                }
            } else {
                // Read/Update properties
                PropertiesConfiguration coinInventory = new PropertiesConfiguration("src/main/resources/config/coin-inventory.properties");
                int coinsRemaining = coinInventory.getInt(String.valueOf(coinDenomination));
                if (coinsRemaining < numberOfCoinsToRelease) {
                    return withdrawSuccess;
                }
                
                for (int i = 0; i < numberOfCoinsToRelease; i++) {
                    Coin coin = new Coin(coinDenomination);
                    theCoinDispenser.offer(coin);
                    coinsRemaining--;
                    coinInventory.setProperty(String.valueOf(coinDenomination), coinsRemaining);
                    coinInventory.save();
                }
                
                withdrawSuccess = true;
            }

            return withdrawSuccess;
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
