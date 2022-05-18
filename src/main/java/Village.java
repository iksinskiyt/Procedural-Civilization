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
    private int teamID;
    private List<Pair<Integer, Integer>> killCounts;
    private Map parentMap;

    Village(Pair<Integer, Integer> position, int teamID,
            Map parentMap) { 

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

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void addVillager(Human villager) {
        villagers.add(villager);
    }

}
