package com.abbasdgr8.vendingmachine.rest;

import java.util.Collection;
import java.util.Iterator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abbasdgr8.vendingmachine.VendingMachine;
import com.abbasdgr8.vendingmachine.model.Coin;

import static com.abbasdgr8.vendingmachine.constants.VendingMachineConstants.HORIZONTAL_RULE;
import static com.abbasdgr8.vendingmachine.constants.VendingMachineConstants.HTML_LINE_BREAK;


/**
 * <pre>
 * A REST resource that exposes the Vending Machine getChange operations at the 
 * address 'http://localhost:8080/vendingMachine'
 * This resource has 2 variants:
 * 1. /getOptimalChange - Assumes there is an unlimited supply of coins.
 * 2. /getChange - Uses an inventory of coins for its operations.
 * </pre>
 *
 * @author Abbas Attarwala
 */
public class GetChangeResource {
    
    VendingMachine vendingMachine = new VendingMachine();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GetChangeResource.class);
    
    @GET
    @Path(value = "/getOptimalChange")
    @Produces(MediaType.TEXT_HTML)
    public String processOptimalChange(@QueryParam("cashInsertedInPence") String cashInsertedInPence,
                                       @QueryParam("productCostInPence") String productCostInPence) {
        
        // Validate the input parameters
        if (!StringUtils.isBlank(inputsAreInvalid(cashInsertedInPence, productCostInPence))) {
            return inputsAreInvalid(cashInsertedInPence, productCostInPence);
        }
        
        StringBuilder responseString = new StringBuilder();
        responseString.append("An unlimited supply of coins.").append(HORIZONTAL_RULE);
        Collection<Coin> coins = null;
        
        //Evaluate the change to be returned
        int changeToBeReturned = Integer.valueOf(cashInsertedInPence) - Integer.valueOf(productCostInPence);
        // Print the change that needs to be returned
        responseString.append("Change to be returned: ").append(changeToBeReturned)
                                                        .append(" pence")
                                                        .append(HTML_LINE_BREAK)
                                                        .append(HTML_LINE_BREAK);
        
        try {
            // Flush the dispenser of existing coins.
            vendingMachine.emptyCoinDispenser();
            // Call to the getOptimalChangeFor method that has an unlimited supply of coins.
            coins = vendingMachine.getOptimalChangeFor(changeToBeReturned);
            // Display the number of coins added to the dispenser
            responseString.append("Coins in dispenser: ").append(coins.size())
                                                         .append(HTML_LINE_BREAK)
                                                         .append(HTML_LINE_BREAK)
                                                         .append(HORIZONTAL_RULE);
            Iterator<Coin> iterator = coins.iterator();
            while (iterator.hasNext()) {
                // Read out each coin
                responseString.append(iterator.next()).append(HTML_LINE_BREAK);
            }
        } catch (Exception ex) {
            LOGGER.error("Exception from REST resource", ex);
            responseString.append("An internal error occured. Read logs to find out more.");
        }
        
        return responseString.toString();
    }
    
    @GET
    @Path(value = "/getChange")
    @Produces(MediaType.TEXT_HTML)
    public String processChange(@QueryParam("cashInsertedInPence") String cashInsertedInPence,
                                @QueryParam("productCostInPence") String productCostInPence) {
        
        // Validate the input parameters
        if (!StringUtils.isBlank(inputsAreInvalid(cashInsertedInPence, productCostInPence))) {
            return inputsAreInvalid(cashInsertedInPence, productCostInPence);
        }
        
        StringBuilder responseString = new StringBuilder();
        responseString.append("A limited inventory of coins at - src/main/resources/META-INF/config/coin-inventory.properties")
                      .append(HORIZONTAL_RULE);
        Collection<Coin> coins = null;
        
        //Evaluate the change to be returned
        int changeToBeReturned = Integer.valueOf(cashInsertedInPence) - Integer.valueOf(productCostInPence);
        // Print the change that needs to be returned
        responseString.append("Change to be returned: ").append(changeToBeReturned).append(" pence")
                                                                                   .append(HTML_LINE_BREAK)
                                                                                   .append(HTML_LINE_BREAK);
        
        try {
            // Flush the dispenser of existing coins.
            vendingMachine.emptyCoinDispenser();
            // Call to the getChangeFor method that gets coins from the inventory.
            coins = vendingMachine.getChangeFor(changeToBeReturned);
            // Display the number of coins added to the dispenser
            responseString.append("Coins in dispenser: ").append(coins.size())
                                                         .append(HTML_LINE_BREAK)
                                                         .append(HTML_LINE_BREAK)
                                                         .append(HORIZONTAL_RULE);
            Iterator<Coin> iterator = coins.iterator();
            while (iterator.hasNext()) {
                // Read out each coin
                responseString.append(iterator.next()).append(HTML_LINE_BREAK);
            }
        } catch (Exception ex) {
            LOGGER.error("Exception from REST resource", ex);
            responseString.append("An internal error occured. Read logs to find out more.");
        }
        
        return responseString.toString();
    }

    /**
     * A validator method for the input parameters of the REST resource.
     * 
     * @param cashInsertedInPence
     * @param productCostInPence
     * @return An empty string if input is valid. Returns invalid response otherwise.
     */
    private String inputsAreInvalid(String cashInsertedInPence, String productCostInPence) {
        
        StringBuilder responseString = new StringBuilder();
        
        if (StringUtils.isBlank(cashInsertedInPence)) {
            responseString.append("A numeric value for cashInsertedInPence is mandatory")
                          .append(HTML_LINE_BREAK);
        }
        
        if (StringUtils.isBlank(productCostInPence)) {
            responseString.append("A numeric value for productCostInPence is mandatory")
                          .append(HTML_LINE_BREAK);
        }
        
        if (!StringUtils.isNumeric(cashInsertedInPence)) {
            responseString.append("cashInsertedInPence must be numeric")
                          .append(HTML_LINE_BREAK);
        }
        
        if (!StringUtils.isNumeric(productCostInPence)) {
            responseString.append("productCostInPence must be numeric")
                          .append(HTML_LINE_BREAK);
        }
        
        if (cashInsertedInPence.length() > 5) {
            responseString.append("cashInsertedInPence must be between 0-9999")
                          .append(HTML_LINE_BREAK);
        }
        
        if (productCostInPence.length() > 5) {
            responseString.append("productCostInPence must be between 0-9999")
                          .append(HTML_LINE_BREAK);
        }
        
        return responseString.toString();
    }
}
