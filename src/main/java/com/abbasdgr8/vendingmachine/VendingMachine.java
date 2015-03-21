package com.abbasdgr8.vendingmachine;

import com.abbasdgr8.vendingmachine.constants.Denomination;
import com.abbasdgr8.vendingmachine.model.Coin;
import java.util.Collection;

/**
 * 
 *
 * @author Abbas Attarwala
 */
public class VendingMachine {
    
    public Collection<Coin> getOptimalChangeFor(int pence) {
        
        for (Denomination d : Denomination.values()) {
            d.getCoinValue();
        }
        
        return null;
        
    }
    
}
