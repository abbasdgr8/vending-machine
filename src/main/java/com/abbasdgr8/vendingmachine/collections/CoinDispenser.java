package com.abbasdgr8.vendingmachine.collections;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abbasdgr8.vendingmachine.exceptions.CoinInventoryError;
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
    
    /**
     * A static instance of {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * capable of holding a maximum of 100 coins at a time.
     */
    private static final CoinDispenser theCoinDispenser = new CoinDispenser(100);
    
    /**
     * The singleton instance of a {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser.CoinBank}
     */
    private final CoinBank coinBank = CoinBank.getInstance();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CoinDispenser.class);
    
    /**
     * A static 'instance' method that returns the same instance of {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser}
     * each time the method is called.
     * 
     * @return 
     */
    public static CoinDispenser getInstance() {
        return theCoinDispenser;
    }
    
    /**
     * A thread-safe method that releases a certain number of {@link com.abbasdgr8.vendingmachine.model.Coin}s
     * to the {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser} and returns a boolean value of 
     * 'true' on success.
     * 
     * @param coinDenomination  The denomination of the {@link com.abbasdgr8.vendingmachine.model.Coin} to release
     * @param numberOfCoinsToRelease The number of {@link com.abbasdgr8.vendingmachine.model.Coin}s to release
     * @return  true/false depending release success/failur
     * @throws NoSuchDenominationException
     * @throws CoinInventoryError 
     */
    public synchronized boolean getCoins(int coinDenomination, int numberOfCoinsToRelease) 
                                            throws NoSuchDenominationException, CoinInventoryError {
        
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
     * bank each time {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser.CoinBank#getInstance()}
     * is called.
     *
     * @author Abbas Attarwala
     */
    private static class CoinBank {

        private boolean unlimitedSupplyOfCoins;
        
        /**
         * A static instance of a {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser.CoinBank}
         */
        private static final CoinBank theCoinBank = new CoinBank();

        /**
         * A static 'instance' method that returns the same instance of
         * {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser.CoinBank} each time
         * the method is called.
         *
         * @return
         */
        public static CoinBank getInstance() {
            return theCoinBank;
        }

        /**
         * A thread-safe method that allows the withdrawal of {@link com.abbasdgr8.vendingmachine.model.Coin}s
         * which returns 'true' on withdrawal success and 'false' on withdrawal failure.
         * This supports two methods of operation. 
         * If the value of {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser.CoinBank#unlimitedSupplyOfCoins}
         * is 'false', it reads/updates a coin-inventory file which would eventually exhaust. 
         * If 'true', it assumes an unlimitedSupplyOfCoins.
         * 
         * @param coinDenomination
         * @param numberOfCoinsToRelease
         * @return
         * @throws NoSuchDenominationException
         * @throws CoinInventoryError 
         */
        public synchronized boolean withdraw(int coinDenomination, int numberOfCoinsToRelease) throws NoSuchDenominationException, CoinInventoryError {
            boolean withdrawSuccess = false;
            
            if (unlimitedSupplyOfCoins) {
                for (int i = 0; i < numberOfCoinsToRelease; i++) {
                    Coin coin = new Coin(coinDenomination);
                    theCoinDispenser.offer(coin);
                    withdrawSuccess = true;
                }
            } else {
                // Read/Update properties
                PropertiesConfiguration coinInventory = null;
                
                try {
                    coinInventory = new PropertiesConfiguration("src/main/resources/META-INF/config/coin-inventory.properties");
                } catch (ConfigurationException ex) {
                    LOGGER.error("There was a problem reading from the Coin inventory", ex);
                    throw new CoinInventoryError("The inventory could not be read", ex);
                }
                
                int coinsRemaining = coinInventory.getInt(String.valueOf(coinDenomination));
                if (coinsRemaining < numberOfCoinsToRelease) {
                    return withdrawSuccess;
                }
                
                for (int i = 0; i < numberOfCoinsToRelease; i++) {
                    Coin coin = new Coin(coinDenomination);
                    theCoinDispenser.offer(coin);
                    coinsRemaining--;
                    coinInventory.setProperty(String.valueOf(coinDenomination), coinsRemaining);
                    try {
                        coinInventory.save();
                    } catch (ConfigurationException ex) {
                        LOGGER.error("There was a problem saving details to the Coin inventory", ex);
                        throw new CoinInventoryError("The inventory could not be saved", ex);
                    }
                }
                
                withdrawSuccess = true;
            }

            return withdrawSuccess;
        }

        /**
         * A way of depositing {@link com.abbasdgr8.vendingmachine.model.Coin}s
         * to the {@link com.abbasdgr8.vendingmachine.collections.CoinDispenser.CoinBank}
         * 
         * @param coinsToDeposit
         * @return 
         */
        public synchronized boolean deposit(Collection<Coin> coinsToDeposit) {
            // No implementation yet. Always returns false - Deposit failure
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
        private CoinBank() { }
        
    }
}
