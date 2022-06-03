package Buildings;

import Simulation.Inventory;
import Simulation.Village;

public class Forge extends Building {

    public static final int forgeWoodCost = 15; // changable forge cost variable
    public static final int forgeStoneCost = 10;
    private static final int armorCost = 5;
    private static final int swordCost = 2;
    private static final int forgeCounter = 15;
    private int tempForgeCounter = forgeCounter;

    public Forge(Village parentVillage) {
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

    private void produceArmor(Inventory inventory) {
        if (inventory.useItem(Inventory.ItemType.LEATHER, armorCost)) {
            inventory.addItem(Inventory.ItemType.ARMOR, 1);
            parentVillage.increaseArmorCount();
        }

    }

    private void produceSword(Inventory inventory) {
        if (inventory.useItem(Inventory.ItemType.STONE, swordCost)) {
            inventory.addItem(Inventory.ItemType.SWORD, 1);
            parentVillage.increaseSwordCount();
        }
    }
}