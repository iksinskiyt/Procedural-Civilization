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

    private List<Human> villagers;
    private List<Building> buildings;
    private Inventory inventory;
<<<<<<< HEAD
    private Pair<Integer, Integer> position;
    private int teamID;
    private List<Pair<Integer, Integer>> killCounts;
    private Map parentMap;

    Village(Pair<Integer, Integer> position, int teamID,
            Map parentMap) { 
=======
    private final Position position;
    // NOTE: change to tuple in documentation
    private int teamID;
    private List<KillCount> killCounts;
    private final Map parentMap;
>>>>>>> Make_it_work

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

        for (Building building : buildings)
            building.simulationTick();

        /* 
        TODO: co ticki sprawdza, czy wszyscy villagerzy maja armor i weapon, jesli nie, to tworzy je i wsadza do inventory wioski...
        TODO: ...villager jest w wiosce i od razu sobie zaklada armor / weapon, podnosi to jego zycie i atak
        */
    }

    public void storeItems(Inventory storedInventory) {
    }

    public void getItems(Inventory neededItems){// NOTE: add to docs
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
