public class Forge extends Building {
    
    public static int forgeWoodCost = 15; // changable forge cost variable
    public static int forgeStoneCost = 10;

    public Forge(Village parentVillage){
        super(parentVillage);
    }

    @Override
    public void simulationTick(){

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
}