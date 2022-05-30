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
    private final List<Building> buildings;
    private Inventory inventory;
    private final Position position;
    private int teamID;
    private List<KillCount> killCounts;
    private final Map parentMap;

    private int houseKillCounter = 15; // changable tick rate untill villager dies from homelessness
    private int tempHouseKillCounter = houseKillCounter;
    private int houseSize = 12; // changable house capacity
    private int forgeCapacity = 50; // changable forge workspace capacity
    private int bakeryCapacity = 25;

    Village(Position position, int teamID,
            Map parentMap) {
        this.position = position;
        this.teamID = teamID;
        this.parentMap = parentMap;
        buildings = new ArrayList<>();
        villagers = new ArrayList<>();
    }

    public void simulationTick() {
        if (villagers.isEmpty()) {
            killCounts.sort(new KillCountComparator());
            teamID = killCounts.get(killCounts.size() - 1).teamID;
        }

        for (Human human : villagers)
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
                }
            }
            else{
                tempHouseKillCounter = houseKillCounter;
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
    }

    public void storeItems(Inventory storedInventory) {
    }

    public void getItems(Inventory neededItems){// NOTE: add to docs
    }

    public boolean addHouse(Inventory inventory){
        Building house = new House(this);
        if(((House)house).createHouse(inventory)){
            buildings.add(house);
            return true;
        }
        else return false;
    }

    public void addForge(Inventory inventory){
        Building forge =  new Forge(this);
        if (((Forge)forge).createForge(inventory)){
            buildings.add(forge);
        }
    }

    public void addBakery(Inventory inventory){
        Building bakery = new Bakery(this);
        if (((Bakery)bakery).createBakery(inventory)){
            buildings.add(bakery);
        }
    }

    public int getTeamID() {
        return teamID;
    }

    public void killVillager(Human villager, int killerTeamID) {
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
}
