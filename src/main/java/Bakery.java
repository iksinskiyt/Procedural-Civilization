public class Bakery extends Building {
    // Yes, the bakery bakes bread and meat... don't ask
    public static int bakeryStoneCost = 20;
    public static int meatCost = 5;
    public static int breadCost = 10 ;
    private int bakeryCounter = 15; // how many ticks will it take to produce food from meat
    private int tempBakeryCounter = bakeryCounter;

    public Bakery(Village parentVillage){
        super(parentVillage);
    }

    @Override
    public void simulationTick() {
        if(tempBakeryCounter-- == 0){
            tempBakeryCounter = bakeryCounter;
            produceFood(parentVillage.getInventory());
        }
    }

    public boolean createBakery(Inventory inventory){
        Item.ItemType stone = Item.ItemType.STONE;
        Item stoneItem = new Item(stone);
        if (inventory.useItem(stoneItem, bakeryStoneCost)){
            return true;
        }
        else return false;
    }

    public void produceFood(Inventory inventory){
        Item meat = new Item(Item.ItemType.MEAT);
        Item bread = new Item(Item.ItemType.WHEAT);
        if(inventory.useItem(meat, meatCost)){ // why does it use 5 meat to make 1 food? don't ask me, i have no idea, its 01:30AM and it's gotta be optimized somehow... maybe the meat from ladybugstore has a fuckton of water inside
            Item food = new Item(Item.ItemType.FOOD); // i hope me swearing in those commits and comment's won't affect our final grade lol
            inventory.addItem(food, 1); // why the hell does the bakery bake meat?????? what????????
        }
        if(inventory.useItem(bread, breadCost)){ 
            Item food = new Item(Item.ItemType.FOOD);
            inventory.addItem(food, 1);
        }
    }
}