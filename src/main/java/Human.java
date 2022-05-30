public class Human extends Creature {
    private final int teamID;
    private boolean onExpedition;
    private final Village parentVillage;

    public Human(int teamID, Map parentMap, Village parentVillage,
                 Position position) {
        super(parentMap, position, 100, 10, 8, 16);

        this.teamID = teamID;
        this.parentVillage = parentVillage;
    }

    public boolean isOnExpedition() {
        return onExpedition;
    }

    public void goToExpedition() {
        onExpedition = true;
    }

    public Village getParentVillage() {
        return parentVillage;
    }

    public int getTeamID() {
        return teamID;
    }

    @Override
    public void move() {
        if (onExpedition) super.move();
        else {
            if (position == parentVillage.getPosition()) {
                parentVillage.storeItems(inventory);
                inventory.clear();
                onExpedition = true;
            } else {
                Position newRandomPosition = getNewRandomPosition();
                // If the new position is not in OCEAN biome and is closer to
                // the parent village than the current position
                if (parentMap.getBiomeAt(newRandomPosition) !=
                        BiomeConverter.Biome.OCEAN &&
                        (Position.squaredDistanceBetween(newRandomPosition,
                                parentVillage.getPosition()) <
                                Position.squaredDistanceBetween(position,
                                        parentVillage.getPosition())))
                    position = newRandomPosition;
            }
        }

        Creature metCreature = parentMap.getNearestAttackableCreature(this);

        if (metCreature != null) {
            metCreature.attack(attackStrength);
            if (!metCreature.isAlive()) {
                Inventory killeeInventory = metCreature.takeInventory();
                inventory.append(killeeInventory);
            }
        }

        if (inventory.isOverflowed()) {
            onExpedition = false;
        } else {
            Item collectedResource = parentMap.collectResource(this);
            inventory.addItem(collectedResource);
        }
    }

    @Override
    public void attack(int damage) {
        health -= damage;
        if (health <= 0) parentVillage.killVillager(this, teamID);
    }
}
