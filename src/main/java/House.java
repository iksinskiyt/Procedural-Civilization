public class House extends Building {
    
    public static int houseCost = 25; // changable house cost variable

    public House(Village parentVillage){
        super(parentVillage);
    }

    @Override
    public void simulationTick(){
        
    }

    public boolean createHouse(Inventory inventory){
        Item.ItemType itemType = Item.ItemType.WOOD;
        Item item = new Item(itemType);
        if (inventory.useItem(item, houseCost)){
            return true;
        }
        else{
            return false;
        } 
    }
}