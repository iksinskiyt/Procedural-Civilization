import org.javatuples.Pair;

import java.util.Comparator;
import java.util.List;

public class Village {
    private class KillCountComparator
            implements Comparator<Pair<Integer, Integer>> {
        @Override
        public int compare(Pair<Integer, Integer> objects,
                           Pair<Integer, Integer> t1) {
            return objects.getValue1() - t1.getValue1();
        }
    }

    private List<Human> villagers;
    private List<Building> buildings;
    private Inventory inventory;
    private Pair<Integer, Integer> position;
    // NOTE: change to tuple in documentation
    private int teamID;
    private List<Pair<Integer, Integer>> killCounts;
    private Map parentMap;

    Village(Pair<Integer, Integer> position, int teamID,
            Map parentMap) { // NOTE: change to tuple in documentation

    }

    public void simulationTick() {
        if (villagers.isEmpty()) {
            killCounts.sort(new KillCountComparator());
            teamID = killCounts.get(killCounts.size() - 1).getValue0();
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

    public Pair<Integer, Integer> getPosition() {
        return position;
    } // NOTE: change to tuple in documentation

    public void addVillager(Human villager) {
        villagers.add(villager);
    }

}
