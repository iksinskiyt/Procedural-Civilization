package Buildings;

import Simulation.Inventory;
import Simulation.Village;

/**
 * Forge building
 */
class Forge extends Building {
    /**
     * How many leather items does it take to produce a single armor item
     */
    private static final int armorCost = 5;

    /**
     * How many stone items does it take to produce a single sword item
     */
    private static final int swordCost = 2;

    /**
     * How many ticks does it take to produce sword and/or armor
     */
    private static final int forgeCounter = 15;

    /**
     * A variable used to remember how many ticks the forge has to wait before
     * taking any action
     */
    private int tempForgeCounter = forgeCounter;

    /**
     * Construct a new forge
     *
     * @param parentVillage A village where the forge is located
     */
    Forge(Village parentVillage) {
        super(parentVillage);
    }

    @Override
    public void simulationTick() {
        if (tempForgeCounter-- == 0) {
            tempForgeCounter = forgeCounter;
            if (parentVillage.getArmorCount() <
                    parentVillage.getVillagers().size()) {
                produceArmor(parentVillage.getInventory());
            }
            if (parentVillage.getSwordCount() <
                    parentVillage.getVillagers().size()) {
                produceSword(parentVillage.getInventory());
            }
        }
    }

    /**
     * Produce armor from items found in inventory. Created armor is inserted
     * back into the given inventory.
     *
     * @param inventory Inventory to take leather from and to place armor to
     */
    private void produceArmor(Inventory inventory) {
        if (inventory.useItem(Inventory.ItemType.LEATHER, armorCost)) {
            inventory.addItem(Inventory.ItemType.ARMOR, 1);
            parentVillage.increaseArmorCount();
        }

    }

    /**
     * Produce a sword from items found in inventory. Created sword is inserted
     * back into the given inventory.
     *
     * @param inventory Inventory to take stone from and to place a sword to
     */
    private void produceSword(Inventory inventory) {
        if (inventory.useItem(Inventory.ItemType.STONE, swordCost)) {
            inventory.addItem(Inventory.ItemType.SWORD, 1);
            parentVillage.increaseSwordCount();
        }
    }
}