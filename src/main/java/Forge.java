public class Forge extends Building {
    
    private static int forgeWoodCost = 15; // changable forge cost variable
    private static int forgeStoneCost = 10;
    private static int armorCost = 5;
    private static int swordCost = 2;
    private static int forgeCounter = 15;
    private int tempForgeCounter = forgeCounter;

    public Forge(Village parentVillage){
        super(parentVillage);
    }

    @Override
    public void simulationTick(){
        if(tempForgeCounter-- == 0){
            tempForgeCounter = forgeCounter;
            if(parentVillage.getArmorCount() < parentVillage.getVillagers().size()){
            produceArmor(parentVillage.getInventory());
            }
            if(parentVillage.getSwordCount() < parentVillage.getVillagers().size()){
            produceSword(parentVillage.getInventory());
            }
        }
    }

    public boolean createForge(Inventory inventory){
        if (inventory.isEnough(Inventory.ItemType.WOOD, forgeWoodCost)){ // quite a bit unethical, but eh... if it works dont touch it
            if(inventory.useItem(Inventory.ItemType.STONE, forgeStoneCost)){
                inventory.useItem(Inventory.ItemType.WOOD, forgeWoodCost);
                return true;
            }
            else return false;
        }
        else return false;
    }

    private void produceArmor(Inventory inventory){
        if(inventory.useItem(Inventory.ItemType.LEATHER, armorCost)){
            inventory.addItem(Inventory.ItemType.ARMOR, 1);
            parentVillage.increaseArmorCount();
        }

    }

    private void produceSword(Inventory inventory){
        if(inventory.useItem(Inventory.ItemType.STONE, swordCost)){
            inventory.addItem(Inventory.ItemType.SWORD, 1);
            parentVillage.increaseSwordCount();
        }
    }
}