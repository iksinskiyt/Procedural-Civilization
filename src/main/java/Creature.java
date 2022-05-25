public abstract class Creature {
    protected Inventory inventory;
    protected Position position;
    protected int health;
    protected final int attackStrength;
    protected final int speed;
    protected Map parentMap;

    public Creature(Map parentMap, Position position, int health,
                    int attackStrength, int speed, int inventoryCapacity) {
        this.parentMap = parentMap;
        this.position = position;
        inventory = new Inventory(inventoryCapacity);

        this.health = health;
        this.attackStrength = attackStrength;
        this.speed = speed;
    }

    public void attack(int damage) {
        health -= damage;
        if (health <= 0)
            parentMap.killCreature(this);
    }

    public void move() {
        // TODO: Generate acceptable random values
        int deltaX = 0;
        int deltaY = 0;

        position.x += deltaX;
        position.y += deltaY;
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
