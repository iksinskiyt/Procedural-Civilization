public class Forge extends Building {
    
    public static int forgeWoodCost = 15; // changable forge cost variable
    public static int forgeStoneCost = 10;

    public Forge(Item neededMaterial, int buildCost){
        super(neededMaterial, buildCost);
    }

    public static Forge createForge(Inventory inventory){
        Item.ItemType wood = Item.ItemType.WOOD;
        Item.ItemType stone = Item.ItemType.STONE;
        Item woodItem = new Item(wood);
        Item stoneItem = new Item(stone);
        Forge forge = new Forge(woodItem, forgeWoodCost); 
        if (inventory.isEnough(woodItem, forgeWoodCost)){ // quite a bit unethical, but eh... if it works dont touch it
            if(inventory.useItem(stoneItem, forgeStoneCost)){
                inventory.useItem(woodItem, forgeWoodCost);
                return forge;
            }
            else return null;
        }
        else return null;
    }
}