import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Village {
    private class KillCount {
        public final int teamID;
        public int count;

        public KillCount(int teamID, int count) {
            this.teamID = teamID;
            this.count = count;
        }
    }

    private class KillCountComparator implements Comparator<KillCount> {
        @Override
        public int compare(KillCount killCount, KillCount t1) {
            return killCount.count - t1.count;
        }
    }

    private final List<Human> villagers;
    // To prevent concurrent modification of `villagers`
    private final List<Human> deadVillagers;
    private final List<Building> buildings;
    private final Inventory inventory;
    private final Position position;
    private int teamID;
    private final List<KillCount> killCounts;
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
        killCounts = new ArrayList<>();
        buildings.add(new House(this));
    }

    public void simulationTick() {
        if (villagers.isEmpty()) {
            killCounts.sort(new KillCountComparator());
            if(!killCounts.isEmpty())
            {
                teamID = killCounts.get(killCounts.size() - 1).teamID;
                killCounts.clear();
            }
        }

        for (Human human : villagers)
            if(human.isAlive())
                human.move();

        for (Building building : buildings)
            building.simulationTick();

        int houseCount = 1;
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
        House house = new House(this);
        if(house.createHouse(inventory)){
            buildings.add(house);
            return true;
        }
        else return false;
    }

    public void addForge(Inventory inventory){
        Forge forge =  new Forge(this);
        if (forge.createForge(inventory)){
            buildings.add(forge);
        }
    }

    public void addBakery(Inventory inventory){
        Bakery bakery = new Bakery(this);
        if (bakery.createBakery(inventory)){
            buildings.add(bakery);
        }
    }

    public int getTeamID() {
        return teamID;
    }

    public void killVillager(Human villager, int killerTeamID) {
        deadVillagers.add(villager);
        for(KillCount killCount : killCounts)
        {
            if(killCount.teamID == killerTeamID) {
                killCount.count++;
                return;
            }
        }
        killCounts.add(new KillCount(killerTeamID, 1));
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
