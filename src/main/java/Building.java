public class Building {

    public void simulationTick() {
        /*
        bedzie wywolywalo tick na bakery zeby jedzienie robic
        */ 
    }

    public int buildCost;
    public Item neededMaterial;

    public Building(Item neededMaterial, int buildCost){
        this.buildCost = buildCost;
        this.neededMaterial = neededMaterial;
    }
}
