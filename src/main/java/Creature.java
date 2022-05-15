import org.javatuples.Pair;

public class Creature {
    public enum CreatureType {
        HUMAN, COW, HAMSTER
    }

    protected Inventory inventory;
    protected CreatureType creatureType;
    protected Pair<Integer, Integer> position;
    protected int health;
    protected int attackStrength;
    protected Map parentMap;

    public Creature(CreatureType creatureType, Map parentMap, Pair<Integer, Integer> position) {
        int inventoryCapacity;
        switch (creatureType) {
            case HUMAN -> inventoryCapacity = 16;
            case COW -> inventoryCapacity = 4;
            case HAMSTER -> inventoryCapacity = 2;
            default -> inventoryCapacity = 0;
        }
        this.inventory = new Inventory(inventoryCapacity);
    }

    public int getHealth() {
        return health;
    }

    public int attack(int damage) {
        return 0;
    }

    public void move() {
        // TODO: Generate acceptable random values
        int deltaX = 0;
        int deltaY = 0;

        this.position = new Pair<>(this.position.getValue0() + deltaX, this.position.getValue1() + deltaY);
    }

    public CreatureType getType() {
        return creatureType;
    }

    public Map getParentMap() {
        return parentMap;
    }

    public Inventory takeInventory() {
        return inventory;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }
}
