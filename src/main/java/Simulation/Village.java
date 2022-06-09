package Simulation;

import Buildings.Building;
import Creatures.Human;
import Structures.Position;
import Terrain.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Village in the world
 */
public class Village {
    /**
     * List of villagers
     */
    private final List<Human> villagers;

    /**
     * List of villagers who are dead and should be removed from the villagers
     * list. Used to prevent concurrent modification of the aforementioned
     * list.
     */
    private final List<Human> deadVillagers;

    /**
     * List of buildings present in the village
     */
    private final List<Building> buildings;

    /**
     * Inventory object
     */
    private final Inventory inventory;

    /**
     * Village position
     */
    private final Position position;

    /**
     * Team ID of the village's owners
     */
    private int teamID;

    /**
     * Map containing kill count for each Team ID
     */
    private final HashMap<Integer, Integer> killCounts;

    /**
     * Map where the village exists
     */
    private final Map parentMap;

    /**
     * Changeable tick rate until a villager dies from homelessness
     */
    private final int houseKillCounter = 150;

    /**
     * A variable used to remember how many ticks the village has to wait before
     * trying to kill a villager due to his homelessness
     */
    private int tempHouseKillCounter = houseKillCounter;

    /**
     * Changeable house capacity
     */
    private final int houseSize = 12;

    /**
     * Changeable forge workspace capacity
     */
    private final int forgeCapacity = 50;

    /**
     * Changeable baker workspace capacity
     */
    private final int bakeryCapacity = 25;

    /**
     * How many armor items there is available in the village
     */
    private int armorCounter = 0;

    /**
     * How many sword items there is available in the village
     */
    private int swordCounter = 0;

    /**
     * Construct a new village
     *
     * @param position  Village's position
     * @param teamID    Owner Team ID
     * @param parentMap Map where the village exists
     */
    public Village(Position position, int teamID, Map parentMap) {
        this.position = position;
        this.teamID = teamID;
        this.parentMap = parentMap;
        buildings = new ArrayList<>();
        villagers = new ArrayList<>();
        deadVillagers = new ArrayList<>();
        inventory = new Inventory(Integer.MAX_VALUE);
        killCounts = new HashMap<>();
        buildings.add(Building.createNew(Building.BuildingType.HOUSE, this));
    }

    /**
     * Determine the next owner based on kill counts and change the village's
     * Team ID
     */
    private void changeTeam() {
        List<HashMap.Entry<Integer, Integer>> killCountList =
                new ArrayList<>(killCounts.entrySet());
        killCountList.sort(HashMap.Entry.comparingByValue());
        if (!killCountList.isEmpty()) {
            teamID = killCountList.get(killCountList.size() - 1).getKey();
            killCounts.clear();
        }
    }

    /**
     * Try to build a new house in the village. If this is not possible and the
     * kill counter expired, kill a villager.
     */
    private void addHouseOrKill() {
        if (!addBuilding(Building.BuildingType.HOUSE) &&
                tempHouseKillCounter-- == 0) {
            // Kill the last human from the villagers list
            villagers.remove(villagers.size() - 1);
            //TODO: add to deathcount? maybe new statistic
            tempHouseKillCounter = houseKillCounter;
        }
    }

    /**
     * Perform a single simulation tick. If there is no villagers left, change
     * the owner Team ID. Make a move with all villagers and perform actions of
     * all buildings. Try to add new buildings and remove dead villagers from
     * the list.
     */
    public void simulationTick() {
        if (villagers.isEmpty())
            changeTeam();

        for (Human human : villagers)
            if (human.isAlive())
                human.move();

        for (Building building : buildings)
            building.simulationTick();

        if (Building.getNumberOfBuildingsFor(this,
                Building.BuildingType.HOUSE) * houseSize < villagers.size())
            addHouseOrKill();

        if (Building.getNumberOfBuildingsFor(this,
                Building.BuildingType.FORGE) * forgeCapacity <
                villagers.size()) {
            addBuilding(
                    Building.BuildingType.FORGE); // think of some punishment
        }

        if (Building.getNumberOfBuildingsFor(this,
                Building.BuildingType.BAKERY) * bakeryCapacity <
                villagers.size()) {
            addBuilding(Building.BuildingType.BAKERY);
        }

        for (Human human : deadVillagers)
            villagers.remove(human);
        deadVillagers.clear();
    }

    /**
     * Store items from the given inventory in the village's inventory
     *
     * @param storedInventory Inventory to append to the village's inventory
     */
    public void storeItems(Inventory storedInventory) {
        inventory.append(storedInventory);
    }

    /**
     * Add a new building of a given type
     *
     * @param buildingType Type of building to add
     * @return Whether there was enough resources in the inventory to create a
     * new building
     */
    private boolean addBuilding(Building.BuildingType buildingType) {
        if (inventory.useItems(Building.getMaterialCosts(buildingType))) {
            buildings.add(Building.createNew(buildingType, this));
            return true;
        }
        return false;
    }

    /**
     * Get owner Team ID
     *
     * @return Team ID
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * Kill a villager. Add him to the list of dead villagers in order not to
     * create a concurrent modification condition.
     *
     * @param villager     Villager to kill
     * @param killerTeamID Team ID to increment the kill count for
     */
    public void killVillager(Human villager, int killerTeamID) {
        deadVillagers.add(villager);
        killCounts.put(killerTeamID,
                killCounts.getOrDefault(killerTeamID, 0) + 1);
    }

    /**
     * Get the map where the village exists
     *
     * @return Parent map
     */
    public Map getParentMap() {
        return parentMap;
    }

    /**
     * Get village's position
     *
     * @return Position object
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Get village's inventory
     *
     * @return Inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Create a new villager and add him to the villagers list
     */
    public void addVillager() {
        villagers.add(new Human(this, position));
    }

    /**
     * Get villagers list
     *
     * @return Villagers list
     */
    public List<Human> getVillagers() {
        return villagers;
    }

    /**
     * Increment armor counter
     */
    public void increaseArmorCount() {
        armorCounter++;
    }

    /**
     * Increment sword counter
     */
    public void increaseSwordCount() {
        swordCounter++;
    }

    /**
     * Get armor counter
     *
     * @return Armor counter value
     */
    public int getArmorCount() {
        return armorCounter;
    }

    /**
     * Get sword counter
     *
     * @return Sword counter value
     */
    public int getSwordCount() {
        return swordCounter;
    }

    /**
     * Get map icon color for the owner Team ID
     *
     * @return Color object
     */
    public Color getTeamColor() {
        return new Color((teamID & 4) > 0 ? 255 : 0, (teamID & 2) > 0 ? 255 : 0,
                (teamID & 1) > 0 ? 255 : 0);
    }
}
