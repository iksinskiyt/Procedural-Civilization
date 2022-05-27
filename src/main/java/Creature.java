import java.util.Random;

public abstract class Creature {
    protected Inventory inventory;
    protected Position position;
    protected int health;
    protected final int attackStrength;
    protected final int speed;
    protected Map parentMap;
    protected Random random;

    public Creature(Map parentMap, Position position, int health,
                    int attackStrength, int speed, int inventoryCapacity) {
        this.parentMap = parentMap;
        this.position = position;
        inventory = new Inventory(inventoryCapacity);

        this.health = health;
        this.attackStrength = attackStrength;
        this.speed = speed;
        random = new Random();
    }

    public void attack(int damage) {
        health -= damage;
        if (health <= 0)
            parentMap.killCreature(this);
    }

    public void move() {
        position.x = Math.min(Math.max(0, position.x +
                        random.nextInt(speed) * (random.nextBoolean() ? 1 : -1)),
                parentMap.getMapSize());
        position.y = Math.min(Math.max(0, position.y +
                        random.nextInt(speed) * (random.nextBoolean() ? 1 : -1)),
                parentMap.getMapSize());
    }

    public Map getParentMap() {
        return parentMap;
    }

    public Inventory takeInventory() {
        return inventory;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
