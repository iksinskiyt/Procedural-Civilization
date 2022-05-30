public abstract class Building {

    public abstract void simulationTick();
    protected final Village parentVillage; 

    public Building(Village parentVillage){ // for right now (by for now i mean forever), its done poorly, object thinks the only material its made off is wood, but while creation, it uses other materials also
        this.parentVillage = parentVillage;
    }
}