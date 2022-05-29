public class Building {

    public void simulationTick() {
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
