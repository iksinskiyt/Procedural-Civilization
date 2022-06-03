package Buildings;

import Simulation.Village;

public abstract class Building {
    public static enum BuildingType {
        BAKERY, FORGE, HOUSE
    }

    public abstract void simulationTick();

    protected final Village parentVillage;

    Building(
            Village parentVillage) { // for right now (by for now i mean forever), its done poorly, object thinks the only material its made off is wood, but while creation, it uses other materials also
        this.parentVillage = parentVillage;
    }

    public static Building createNew(BuildingType buildingType,
                                     Village parentVillage) {
        switch (buildingType) {
            case BAKERY -> {
                return new Bakery(parentVillage);
            }
            case FORGE -> {
                return new Forge(parentVillage);
            }
            case HOUSE -> {
                return new House(parentVillage);
            }
        }
        return null;
    }
}