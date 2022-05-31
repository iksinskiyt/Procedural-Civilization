public class House extends Building {
    
    private static int houseCost = 25; // changable house cost variable
    private int sexCounter = 420;
    private int tempSexCounter = sexCounter;

    public House(Village parentVillage){
        super(parentVillage);
    }

    @Override
    public void simulationTick(){
        if(tempSexCounter-- == 0){
            tempSexCounter = sexCounter;
            Human human = new Human(parentVillage.getTeamID(), parentVillage.getParentMap(), parentVillage, parentVillage.getPosition());
            parentVillage.addVillager(human);
        }
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