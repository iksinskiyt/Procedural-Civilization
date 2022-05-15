import org.javatuples.Pair;

public class Creature {
    public enum CreatureType {
        HUMAN,
        COW,
        HAMSTER
    }

    private Inventory inventory;
    private CreatureType creatureType;
    private Pair<Integer, Integer> position;
    private int health;
    private int attackStrength;
    private Map parentMap;

    public Creature(CreatureType creatureType, Map parentMap, Pair<Integer, Integer> position) {}
    public int getHealth() {return health;}
    public int attack(int damage) {return 0;}
    public void move() {}
    public CreatureType getType() {return creatureType;}
    public Map getParentMap() {return parentMap;}
    public Inventory takeInventory() {return inventory;}
    public Pair<Integer, Integer> getPosition() {return position;}
}
