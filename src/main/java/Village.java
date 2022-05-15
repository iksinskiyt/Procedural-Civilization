import org.javatuples.Pair;
import java.util.List;

public class Village {
    private List<Human> villagers;
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
    public int getTeamID(){return teamID;}
    public void killVillager(Human villager, int killerTeamID){}
    public Map getParentMap(){return parentMap;}
    public Pair<Integer, Integer> getPosition(){return position;} // NOTE: change to tuple in documentation
    public void addVillager(Human villager){
        this.villagers.add(villager);
    }

}
