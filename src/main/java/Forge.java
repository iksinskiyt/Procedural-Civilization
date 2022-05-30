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
        Item.ItemType wood = Item.ItemType.WOOD;
        Item.ItemType stone = Item.ItemType.STONE;
        Item woodItem = new Item(wood);
        Item stoneItem = new Item(stone);
        if (inventory.isEnough(woodItem, forgeWoodCost)){ // quite a bit unethical, but eh... if it works dont touch it
            if(inventory.useItem(stoneItem, forgeStoneCost)){
                inventory.useItem(woodItem, forgeWoodCost);
                return true;
            }
            else return false;
        }
        else return false;
    }

    private void produceArmor(Inventory inventory){
        Item leather = new Item(Item.ItemType.LEATHER);
        if(inventory.useItem(leather, armorCost)){
            Item armor = new Item(Item.ItemType.ARMOR);
            inventory.addItem(armor, 1);
            parentVillage.increaseArmorCount();
        }

    }

    private void produceSword(Inventory inventory){
        Item stone = new Item(Item.ItemType.STONE);
        if(inventory.useItem(stone, swordCost)){
            Item sword = new Item(Item.ItemType.SWORD);
            inventory.addItem(sword, 1);
            parentVillage.increaseSwordCount();
        }
    }
}