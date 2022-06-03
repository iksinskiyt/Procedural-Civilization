package Simulation;

import Buildings.Building;
import Creatures.Human;
import Structures.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Village {
    private final List<Human> villagers;
    // To prevent concurrent modification of `villagers`
    private final List<Human> deadVillagers;
    private final List<Building> buildings;
    private final Inventory inventory;
    private final Position position;
    private int teamID;
    // HashMap of teamID and kill count
    private final HashMap<Integer, Integer> killCounts;
    private final Map parentMap;

    private final int houseKillCounter = 150;
    // changable tick rate untill villager dies from homelessness
    private int tempHouseKillCounter = houseKillCounter;
    private final int houseSize = 12; // changable house capacity
    private final int forgeCapacity = 50; // changable forge workspace capacity
    private final int bakeryCapacity = 25;
    private int armorCounter = 0;
    private int swordCounter = 0;

    Village(Position position, int teamID, Map parentMap) {
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

    public void simulationTick() {
        if (villagers.isEmpty()) {
            List<HashMap.Entry<Integer, Integer>> killCountList =
                    new ArrayList<>(killCounts.entrySet());
            killCountList.sort(HashMap.Entry.comparingByValue());
            if (!killCountList.isEmpty()) {
                teamID = killCountList.get(killCountList.size() - 1).getKey();
                killCounts.clear();
            }
        }

        for (Human human : villagers)
            if (human.isAlive())
                human.move();

        for (Building building : buildings)
            building.simulationTick();

        if (Building.getNumberOfBuildingsFor(this,
                Building.BuildingType.HOUSE) * houseSize < villagers.size()) {
            if (!addBuilding(Building.BuildingType.HOUSE)) {
                if (tempHouseKillCounter-- == 0) {
                    villagers.remove(
                            villagers.size() - 1); // kills last human from list
                    //TODO: add to deathcount? maybe new statistic
                    tempHouseKillCounter = houseKillCounter;
                }
            }
        }
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
        /* 
        TODO: co ticki sprawdza, czy wszyscy villagerzy maja armor i weapon, jesli nie, to tworzy je i wsadza do inventory wioski
        villager jest w wiosce i od razu sobie zaklada armor / weapon, podnosi to jego zycie i atak
        */

        for (Human human : deadVillagers)
            villagers.remove(human);
        deadVillagers.clear();
    }

    public void storeItems(Inventory storedInventory) {
        inventory.append(storedInventory);
    }

    private boolean addBuilding(Building.BuildingType buildingType) {
        if (inventory.useItems(Building.getMaterialCosts(buildingType))) {
            buildings.add(Building.createNew(buildingType, this));
            return true;
        }
        return false;
    }

    public int getTeamID() {
        return teamID;
    }

    public void killVillager(Human villager, int killerTeamID) {
        deadVillagers.add(villager);
        killCounts.put(killerTeamID,
                killCounts.getOrDefault(killerTeamID, 0) + 1);
    }

    public Map getParentMap() {
        return parentMap;
    }

    public Position getPosition() {
        return position;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addVillager() {
        villagers.add(Human.createNew(this, position));
    }

    public List<Human> getVillagers() {
        return villagers;
    }

    public void increaseArmorCount() {
        armorCounter++;
    }

    public void increaseSwordCount() {
        swordCounter++;
    }

    public int getArmorCount() {
        return armorCounter;
    }

    public int getSwordCount() {
        return swordCounter;
    }
}
