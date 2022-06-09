package Buildings;

import Simulation.Inventory;
import Simulation.Village;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * An abstract class describing a building in a village
 */
public abstract class Building {
    /**
     * Building type
     */
    public enum BuildingType {
        /**
         * Bakery - bakes wheat and meat to produce food <br>
         * <i>Yes, the bakery bakes bread and meat... don't ask</i>
         */
        BAKERY,

        /**
         * Forge - produces armor from leather and swords from stone
         */
        FORGE,

        /**
         * House - allows more villagers to be born
         */
        HOUSE
    }

    /**
     * Nested map containing material costs for each building type
     */
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

    /**
     * Nested map containing a number of buildings of each type created for each
     * village
     */
    private static final HashMap<Village, EnumMap<BuildingType, Integer>>
            buildingsCreatedFor = new HashMap<>();

    /**
     * Perform an action specific to the given building type
     */
    public abstract void simulationTick();

    /**
     * Village where the building is located
     */
    protected final Village parentVillage;

    /**
     * Construct a new building
     *
     * @param parentVillage A village where the building is located
     */
    Building(Village parentVillage) {
        this.parentVillage = parentVillage;
    }

    /**
     * Create a new building of a given type
     *
     * @param buildingType  Type of building to be created
     * @param parentVillage A village where the building is located
     * @return A new building object
     */
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

    /**
     * Get number of buildings of a given type created for a given village
     *
     * @param village      Village to get the count for
     * @param buildingType Type of buildings to get count for
     * @return Number of buildings
     */
    public static int getNumberOfBuildingsFor(Village village,
                                              BuildingType buildingType) {
        EnumMap<BuildingType, Integer> numberOfBuildings =
                buildingsCreatedFor.get(village);
        if (numberOfBuildings == null)
            return 0;
        else
            return numberOfBuildings.getOrDefault(buildingType, 0);
    }

    /**
     * Get material costs for a given building type
     *
     * @param buildingType Building type to get costs for
     * @return A map containing cost for each item type
     */
    public static EnumMap<Inventory.ItemType, Integer> getMaterialCosts(
            BuildingType buildingType) {
        return materialCosts.get(buildingType);
    }
}