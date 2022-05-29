public class Building {

    private int bakeryCounter = 15; // how many ticks will it take to produce food from meat
    private int tempBakeryCounter = bakeryCounter;

    public void simulationTick(Inventory inventory) {
        if(tempBakeryCounter-- == 0){
            tempBakeryCounter = bakeryCounter;
            Bakery.produceFood(inventory);
        }


        /*
        bedzie wywolywalo tick na bakery zeby jedzienie robic
        */ 

    }

    public int buildCost;
    public Item neededMaterial;

    public Building(Item neededMaterial, int buildCost){ // for right now (by for now i mean forever), its done poorly, object thinks the only material its made off is wood, but while creation, it uses other materials also
        this.buildCost = buildCost;
        this.neededMaterial = neededMaterial;
    }
}
