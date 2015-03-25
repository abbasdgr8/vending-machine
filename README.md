This example provides an implementation of the coin vending capabilities of a real-life vending machine. This example when built, will give a spring-bootable JAR that contains a light-weight Jetty server that would serve two REST resources built with Apache CXF that display how change is managed by dispensing the least number of coins.
One resource(getOptimalChange) assumes an unlimited supply of coins. Another resource(getChange) uses a property file as a coin inventory to read and update the inventory of coins according to the usage.

##Pre-requisites :
* you need Java 1.7.0_51 JDK installed and in your path
* you need Maven 3.0.5 installed and in your path

##To Build the Vending Machine from source:
1. Clone this repository
2. Change directory into the `vending-machine/` directory and run the following from the command line: $ `mvn clean install`
This will compile, test and package the library and create a spring-bootable JAR file.

##To Run:
1. From the command line(or from your IDE) enter the following command: $ `mvn spring-boot:run`
2. Try the [getOptimalChange](http://localhost:8080/vendingMachine/getOptimalChange?cashInsertedInPence=50&productCostInPence=11) REST resource which implements Part-1 of the Java Programming Test.
  1. Go to the URL bar of the browser and change values of the query parameters - `cashInsertedInPence` & `productCostInPence` to test the application.
  2. This REST resource assumes an unlimited supply of coins.
3. Try the [getChange](http://localhost:8080/vendingMachine/getChange?cashInsertedInPence=50&productCostInPence=11) REST resource which implements Part-2 of the Java Programming Test.
  1. Go to the URL bar of the browser and change values of the query parameters - `cashInsertedInPence` & `productCostInPence` to test the application.
  2. This REST resource uses a Coin Inventory located at `src/main/resources/META-INF/config/coin-inventory.properties` to dispense coins. This inventory has a limited supply of coins which will exhaust after a significant number of coin withdrawals.
  3. The coin inventory can be re-filled by simply opening the file in a text-editor and updating the number of coins associated with each denomination.
