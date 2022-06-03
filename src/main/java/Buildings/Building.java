package Buildings;

import Simulation.Inventory;
import Simulation.Village;

import java.util.EnumMap;
import java.util.HashMap;

public abstract class Building {
    public enum BuildingType {
        BAKERY, FORGE, HOUSE
    }

    private static final EnumMap<BuildingType, EnumMap<Inventory.ItemType, Integer>>
            materialCosts = new EnumMap<>(BuildingType.class) {{
        put(BuildingType.BAKERY, new EnumMap<>(Inventory.ItemType.class) {{
            put(Inventory.ItemType.STONE, 20);
        }});
        put(BuildingType.FORGE, new EnumMap<>(Inventory.ItemType.class) {{
            put(Inventory.ItemType.WOOD, 15);
            put(Inventory.ItemType.STONE, 10);
        }});
        put(BuildingType.HOUSE, new EnumMap<>(Inventory.ItemType.class) {{
            put(Inventory.ItemType.WOOD, 25);
        }});
    }};

    private static final HashMap<Village, EnumMap<BuildingType, Integer>>
            buildingsCreatedFor = new HashMap<>();

    public abstract void simulationTick();

    protected final Village parentVillage;

    Building(
            Village parentVillage) { // for right now (by for now i mean forever), its done poorly, object thinks the only material its made off is wood, but while creation, it uses other materials also
        this.parentVillage = parentVillage;
    }

    public static Building createNew(BuildingType buildingType,
                                     Village parentVillage) {
        EnumMap<BuildingType, Integer> numberOfBuildings =
                buildingsCreatedFor.get(parentVillage);
        if (numberOfBuildings == null)
            numberOfBuildings = new EnumMap<>(BuildingType.class);
        numberOfBuildings.put(buildingType,
                getNumberOfBuildingsFor(parentVillage, buildingType) + 1);
        buildingsCreatedFor.put(parentVillage, numberOfBuildings);
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

    public static int getNumberOfBuildingsFor(Village village,
                                              BuildingType buildingType) {
        EnumMap<BuildingType, Integer> numberOfBuildings =
                buildingsCreatedFor.get(village);
        if (numberOfBuildings == null)
            return 0;
        else
            return numberOfBuildings.getOrDefault(buildingType, 0);
    }

    public static EnumMap<Inventory.ItemType, Integer> getMaterialCosts(
            BuildingType buildingType) {
        return materialCosts.get(buildingType);
    }
}