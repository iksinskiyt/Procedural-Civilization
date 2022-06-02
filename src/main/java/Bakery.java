public class Bakery extends Building {
    // Yes, the bakery bakes bread and meat... don't ask
    public static final int bakeryStoneCost = 20;
    private static final int meatCost = 5;
    private static final int breadCost = 10;
    private static final int bakeryCounter = 15;
    // how many ticks will it take to produce food from meat
    private int tempBakeryCounter = bakeryCounter;

    public Bakery(Village parentVillage) {
        super(parentVillage);
    }

    @Override
    public void simulationTick() {
        if (tempBakeryCounter-- == 0) {
            // tempBakeryCounter = bakeryCunter;
            produceFood(parentVillage.getInventory());
            tempBakeryCounter = bakeryCounter;
        }
    }

    private void produceFood(Inventory inventory) {
        if (inventory.useItem(Inventory.ItemType.MEAT,
                meatCost)) { // why does it use 5 meat to make 1 food? don't ask me, i have no idea, its 01:30AM and it's gotta be optimized somehow... maybe the meat from ladybugstore has a fuckton of water inside
            inventory.addItem(Inventory.ItemType.FOOD,
                    1); // why the hell does the bakery bake meat?????? what????????
        }
        if (inventory.useItem(Inventory.ItemType.WHEAT, breadCost)) {
            inventory.addItem(Inventory.ItemType.FOOD, 1);
        }
    }
}