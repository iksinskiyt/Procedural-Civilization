import java.util.Comparator;
import java.util.List;
import java.util.HashMap;


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

    private List<Human> villagers;
    private HashMap<Building, Integer> buildings;
    private Inventory inventory;
    private final Position position;
    // NOTE: change to tuple in documentation
    private int teamID;
    private List<KillCount> killCounts;
    private final Map parentMap;

    Village(Position position, int teamID,
            Map parentMap) {
        this.position = position;
        this.teamID = teamID;
        this.parentMap = parentMap;
    }

    public void simulationTick() {
        if (villagers.isEmpty()) {
            killCounts.sort(new KillCountComparator());
            teamID = killCounts.get(killCounts.size() - 1).teamID;
        }

        for (Human human : villagers)
            human.move();

        for (Building building : buildings.keySet())
            building.simulationTick();

        if(buildings.get(new House(new Item(Item.ItemType.WOOD), 25)) * 10 < villagers.size()){
            addHouse(this.inventory);
        }

        /* 
        TODO: co ticki sprawdza, czy wszyscy villagerzy maja armor i weapon, jesli nie, to tworzy je i wsadza do inventory wioski
        villager jest w wiosce i od razu sobie zaklada armor / weapon, podnosi to jego zycie i atak

        Sprawdza co tick, czy ilosc domow jest wystarczajaca dla villagerow.
        1 Dom = 12 villagerow

        sprawdza ile jest domow, mnozy razy ilosc miejsc w domach = ilosc dostepnych miejsc
        jesli jest za malo miejsc, tworzy nowy dom
        jest nie moze stworzyc domu, tworzy sie nowy counter co liczy ticki,
        jesli nie zdarza wybudowac dom, ginie czlowiek do momentu kiedy starczy miejsc w domach
        */
    }

    public void storeItems(Inventory storedInventory) {
    }

    public void getItems(Inventory neededItems){// NOTE: add to docs
    }

    public void addHouse(Inventory inventory){
        Building house = House.createHouse(inventory);
        if (house != null){
            int houseAmount = buildings.get(house) + 1;
            buildings.put(house, houseAmount);
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

    public void addVillager(Human villager) {
        villagers.add(villager);
    }

}
