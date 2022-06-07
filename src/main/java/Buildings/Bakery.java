package Buildings;

import Simulation.Inventory;
import Simulation.Village;

/**
 * Bakery building
 */
class Bakery extends Building {
    /**
     * How many meat items does it take to create a single food item
     */
    private static final int meatCost = 5;

    /**
     * How many wheat items does it take to create a single food item
     */
    private static final int breadCost = 10;

    /**
     * How many ticks does it take to produce food from meat and/or wheat
     */
    private static final int bakeryCounter = 15;

    /**
     * A variable used to remember how many ticks the bakery has to wait before
     * taking any action
     */
    private int tempBakeryCounter = bakeryCounter;

    /**
     * Construct a new bakery
     *
     * @param parentVillage A village where the bakery is located
     */
    Bakery(Village parentVillage) {
        super(parentVillage);
    }

    @Override
    public void simulationTick() {
        if (tempBakeryCounter-- == 0) {
            produceFood(parentVillage.getInventory());
            tempBakeryCounter = bakeryCounter;
        }
    }

    /**
     * Produce food from items found in inventory. Created food is inserted back
     * into the given inventory.
     *
     * @param inventory Inventory to take meat and wheat from and to place food
     *                  to
     */
    private void produceFood(Inventory inventory) {
        if (inventory.useItem(Inventory.ItemType.MEAT, meatCost))
            inventory.addItem(Inventory.ItemType.FOOD, 1);
        if (inventory.useItem(Inventory.ItemType.WHEAT, breadCost))
            inventory.addItem(Inventory.ItemType.FOOD, 1);
    }
}