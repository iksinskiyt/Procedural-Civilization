public class House extends Building {
    
    public static int houseCost = 25; // changable house cost variable

    public House(Item neededMaterial, int buildCost){
        super(neededMaterial, buildCost);
    }

    public static House createHouse(Inventory inventory){
        Item.ItemType itemType = Item.ItemType.WOOD;
        Item item = new Item(itemType);
        House house = new House(item, houseCost);
        if (inventory.useItem(item, house.buildCost)){
            return house;
        }
        else{
            return null;
        } 
    }
}