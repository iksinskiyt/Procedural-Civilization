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

        for (Building building : buildings)
            building.simulationTick();
    }

    public void storeItems(Inventory storedInventory) {
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
    } // NOTE: change to tuple in documentation

    public void addVillager(Human villager) {
        villagers.add(villager);
    }

}
