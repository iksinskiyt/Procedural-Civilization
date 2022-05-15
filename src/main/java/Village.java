import org.javatuples.Pair;

public class Village {
    private Human[] villagers;
    private Building[] buildings;
    private Inventory inventory;
    private Pair<Integer, Integer> position; // NOTE: change to tuple in documentation
    private int teamID;
    private Pair<Integer, Integer>[] killCounts;
    private Map parentMap;

    Village(Pair<Integer, Integer> position, int teamID, Map parentMap){ // NOTE: change to tuple in documentation

    }

    public void simulationTick(){}
    public void storeItems(Inventory storedInventory){}
    public int getTeamID(){}
    public void killVillager(Human villager, int killerTeamID){}
    public Map getParentMap(){}
    public Pair<Integer, Integer> getPosition(){} // NOTE: change to tuple in documentation
    public void addVillager(Human villager){}

}
