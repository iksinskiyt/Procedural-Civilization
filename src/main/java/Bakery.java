public class Bakery extends Building {

    public static int bakeryStoneCost = 20;

    public Bakery(Item neededMaterial, int buildCost){
        super(neededMaterial, buildCost);
    }

    public static Bakery createBakery(Inventory inventory){
        Item.ItemType stone = Item.ItemType.STONE;
        Item stoneItem = new Item(stone);
        Bakery bakery = new Bakery(stoneItem, bakeryStoneCost);
        if (inventory.useItem(stoneItem, bakeryStoneCost)){
            return bakery;
        }
        else return null;
    }

}
