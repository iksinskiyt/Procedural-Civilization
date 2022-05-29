public class Bakery extends Building {
    // Yes, the bakery bakes bread and meat... don't ask
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

    public static void produceFood(Inventory inventory){
        Item meat = new Item(Item.ItemType.MEAT);
        Item bread = new Item(Item.ItemType.WHEAT);
        if(inventory.useItem(meat, 5)){ // why does it use 5 meat to make 1 food? don't ask me, i have no idea, its 01:30AM and it's gotta be optimized somehow... maybe the meat from ladybugstore has a fuckton of water inside
            Item food = new Item(Item.ItemType.FOOD); // i hope me swearing in those commits and comment's won't affect our final grade lol
            inventory.addItem(food, 1); // why the hell does the bakery bake meat?????? what????????
        }
        if(inventory.useItem(bread, 10)){ 
            Item food = new Item(Item.ItemType.FOOD);
            inventory.addItem(food, 1);
        }
    }
}