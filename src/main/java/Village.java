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

    private int houseKillCounter = 150; // changable tick rate untill villager dies from homelessness
    private int tempHouseKillCounter = houseKillCounter;
    private int houseSize = 12; // changable house capacity
    private int forgeCapacity = 50; // changable forge workspace capacity
    private int bakeryCapacity = 25;
    private int armorCounter = 0;
    private int swordCounter = 0;

    Village(Position position, int teamID,
            Map parentMap) {
        this.position = position;
        this.teamID = teamID;
        this.parentMap = parentMap;
        buildings = new ArrayList<>();
        villagers = new ArrayList<>();
        deadVillagers = new ArrayList<>();
        inventory = new Inventory(128);
        killCounts = new HashMap<>();
        buildings.add(new House(this));
    }

    public void simulationTick() {
        if (villagers.isEmpty()) {
            List<HashMap.Entry<Integer, Integer>> killCountList = new ArrayList<>(killCounts.entrySet());
            killCountList.sort(HashMap.Entry.comparingByValue());
            if(!killCountList.isEmpty())
            {
                teamID = killCountList.get(killCountList.size() - 1).getKey();
                killCounts.clear();
            }
        }

        for (Human human : villagers)
            if(human.isAlive())
                human.move();

        for (Building building : buildings)
            building.simulationTick();

        int houseCount = 0;
        int forgeCount = 0;
        int bakeryCount = 0;
        for (Building building : buildings){
            if(building instanceof House){
                houseCount++;
            }
            if(building instanceof Forge){
                forgeCount++;
            }
            if(building instanceof Bakery){
                bakeryCount++;
            }
        }
        if(houseCount * houseSize < villagers.size()){
            if (!addHouse(this.inventory)){
                if(tempHouseKillCounter-- == 0){
                    villagers.remove(villagers.size()-1); // kills last human from list
                    //TODO: add to deathcount? maybe new statistic
                    tempHouseKillCounter = houseKillCounter;
                }
            }
        }
        if(forgeCount * forgeCapacity < villagers.size()){
            addForge(this.inventory); // think of some punishment
        }
        if(bakeryCount * bakeryCapacity < villagers.size()){ 
            addBakery(this.inventory);
        }
        /* 
        TODO: co ticki sprawdza, czy wszyscy villagerzy maja armor i weapon, jesli nie, to tworzy je i wsadza do inventory wioski
        villager jest w wiosce i od razu sobie zaklada armor / weapon, podnosi to jego zycie i atak
        */

        for(Human human : deadVillagers)
            villagers.remove(human);
        deadVillagers.clear();
    }

    public void storeItems(Inventory storedInventory) {
        inventory.append(storedInventory);
    }

    public void getItems(Inventory neededItems){// NOTE: add to docs
    }

    public boolean addHouse(Inventory inventory){
        if(inventory.useItem(Inventory.ItemType.WOOD, House.houseWoodCost)) {
            buildings.add(new House(this));
            return true;
        }
        return false;
    }

    public void addForge(Inventory inventory){
        if(inventory.isEnough(Inventory.ItemType.WOOD, Forge.forgeWoodCost) && inventory.isEnough(
                Inventory.ItemType.STONE, Forge.forgeStoneCost))
        {
            inventory.useItem(Inventory.ItemType.WOOD, Forge.forgeWoodCost);
            inventory.useItem(Inventory.ItemType.STONE, Forge.forgeStoneCost);
            buildings.add(new Forge(this));
        }
    }

    public void addBakery(Inventory inventory){
        if(inventory.useItem(Inventory.ItemType.STONE, Bakery.bakeryStoneCost))
            buildings.add(new Bakery(this));
    }

    public int getTeamID() {
        return teamID;
    }

    public void killVillager(Human villager, int killerTeamID) {
        deadVillagers.add(villager);
        killCounts.put(killerTeamID, killCounts.getOrDefault(killerTeamID, 0) + 1);
    }

    public Map getParentMap() {
        return parentMap;
    }

    public Position getPosition() {
        return position;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public void addVillager(Human villager) {
        villagers.add(villager);
    }

    public List<Human> getVillagers()
    {
        return villagers;
    }

    public void increaseArmorCount(){
        armorCounter++;
    }
    public void increaseSwordCount(){
        swordCounter++;
    }

    public int getArmorCount(){
        return armorCounter;
    }

    public int getSwordCount(){
        return swordCounter;
    }
}
