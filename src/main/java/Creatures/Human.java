package Creatures;

import Simulation.Inventory;
import Simulation.Village;
import Structures.Position;

import java.awt.*;
import java.util.Random;

/**
 * Human creature
 */
public class Human extends Creature {
    /**
     * Whether the human is on expedition
     */
    private boolean onExpedition;

    /**
     * Village where the human lives
     */
    private final Village parentVillage;

    /**
     * Number of tries left to take when making a movement towards a certain
     * position before giving up and assuming a random movement pattern
     */
    private int positionTriesLeft = 10;

    /**
     * Number of ticks of random movement before returning to position following
     * movement pattern
     */
    private int randomTicksLeft = 0;

    /**
     * Human maximum health without armor
     */
    private static final int humanBasicHealth = 100;

    /**
     * Counter of armor usages left
     */
    private int hasArmor = 0;

    /**
     * Counter of sword usages left
     */
    private int hasSword = 0;

    /**
     * Maximum health bonus given when wearing armor
     */
    private final int armorBonus = 20;

    /**
     * Attack strength bonus given by sword
     */
    private final int swordBonus = 5;

    /**
     * How many ticks the human has to wait after trying to eat food before
     * trying again
     */
    private final int eatingCounter = 3;

    /**
     * A variable used to remember how many ticks the human has to wait before
     * trying to eat food
     */
    private int tempEatingCounter = 1;

    /**
     * How many health points does a single food item restore
     */
    private final int foodRegenAmount = 10;

    /**
     * Maximum human health
     */
    public int maxHealth = humanBasicHealth;

    /**
     * Number of armor usages while wearing undamaged armor
     */
    public int maxArmor = 8;

    /**
     * Number of sword usages while wielding an undamaged sword
     */
    public int maxSword = 8;

    /**
     * Construct a new human
     *
     * @param parentVillage Village where the human lives
     * @param position      Starting position
     */
    public Human(Village parentVillage, Position position) {
        super(parentVillage.getParentMap(), position, humanBasicHealth, 10, 8,
                16);

        this.parentVillage = parentVillage;
        random = new Random();
    }

    /**
     * Try to equip armor. Succeeds when there is at least one armor item
     * present in the parent village inventory.
     */
    private void tryEquipArmor() {
        if (parentVillage.getInventory().useItem(Inventory.ItemType.ARMOR, 1)) {
            this.hasArmor = maxArmor;
            this.health += armorBonus;
            maxHealth += armorBonus;
        }
    }

    /**
     * Try to equip sword. Succeeds when there is at least one sword item
     * present in the parent village inventory.
     */
    public void tryEquipSword() {
        if (parentVillage.getInventory().useItem(Inventory.ItemType.SWORD, 1)) {
            this.hasSword = maxSword;
        }
    }

    /**
     * Get the human Team ID
     *
     * @return Team ID
     */
    @Override
    public int getTeamID() {
        return parentVillage.getTeamID();
    }

    /**
     * Try to make a move towards the given position
     *
     * @param position Position to move towards
     */
    private void tryMoveTowards(Position position) {
        Position newRandomPosition = getNewRandomPosition();
        if (Position.squaredDistanceBetween(newRandomPosition, position) <
                Position.squaredDistanceBetween(this.position, position)) {
            this.position = newRandomPosition;
            positionTriesLeft = 10;
        }
        else
            positionTriesLeft--;
    }

    /**
     * Make a move towards the given position. If it fails for 10 times in a
     * row, move randomly for the next 100 ticks.
     *
     * @param position Position to move towards
     */
    private void makeMoveTowards(Position position) {
        if (randomTicksLeft > 0) {
            super.move();
            randomTicksLeft--;
        }
        else
            tryMoveTowards(position);
        if (positionTriesLeft <= 0) {
            if (randomTicksLeft <= 0) {
                randomTicksLeft = 100;
                positionTriesLeft = 10;
            }
            else
                randomTicksLeft--;
        }
    }

    /**
     * Get usages of armor left
     *
     * @return Usages of armor left
     */
    public int getArmor() {
        return hasArmor;
    }

    /**
     * Get usages of sword left
     *
     * @return Usages of sword left
     */
    public int getSword() {
        return hasSword;
    }

    /**
     * Get health
     *
     * @return Health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get maximum health
     *
     * @return Current maximum health (including armor effect)
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get maximum armor usages
     *
     * @return Maximum armor usages
     */
    public int getMaxArmor() {
        return maxArmor;
    }

    /**
     * Get maximum sword usages
     *
     * @return Maximum sword usages
     */
    public int getMaxSword() {
        return maxSword;
    }

    /**
     * Attack the given creature
     *
     * @param creature Creature to attack
     */
    private void attackCreature(Creature creature) {
        int randomFuzz = random.nextInt(5) - 2;
        creature.attack(
                attackStrength + randomFuzz + (hasSword > 0 ? swordBonus : 0),
                parentVillage.getTeamID());
        hasSword = Math.max(hasSword - 1, 0);

        if (!creature.isAlive()) {
            Inventory killeeInventory = creature.takeInventory();
            inventory.append(killeeInventory);
        }
    }

    /**
     * Get the nearest enemy creature within vision distance. If there is any,
     * make a move towards it. Make a random move otherwise.
     */
    private void observeAndFollow() {
        Creature seenCreature =
                parentMap.getNearestEnemyWithinDistance(this, 256);
        if (seenCreature != null)
            makeMoveTowards(seenCreature.getPosition());
        else
            super.move();
    }

    /**
     * Try to eat food. Succeeds when the human's health is below maximum and
     * there is at least one food item available in the parent village
     * inventory.
     */
    private void tryEatFood() {
        if (health < humanBasicHealth + (hasArmor > 0 ? armorBonus : 0) &&
                parentVillage.getInventory()
                        .useItem(Inventory.ItemType.FOOD, 1)) {
            health += foodRegenAmount;
            health = Math.min(health,
                    humanBasicHealth + (hasArmor > 0 ? armorBonus : 0));
        }
    }

    /**
     * Perform actions when in village. Store all inventory in the parent
     * village and try to equip armor and sword (if not already equipped). Try
     * to eat food.
     */
    private void inVillageActions() {
        parentVillage.storeItems(inventory);
        inventory.clear();
        onExpedition = true;

        if (hasArmor <= 0)
            tryEquipArmor();

        if (hasSword <= 0)
            tryEquipSword();

        tempEatingCounter--;
        if (tempEatingCounter == 0) {
            tempEatingCounter = eatingCounter;
            tryEatFood();
        }
    }

    /**
     * Try to collect resources from the map and add them to own inventory
     */
    private void tryCollectResource() {
        Inventory.ItemType collectedResource = parentMap.collectResource(this);
        if (collectedResource != null)
            inventory.addItem(collectedResource, 1);
    }

    /**
     * Make a complete human move. Try to hunt something (or somebody), follow
     * enemies and perform in-village actions. If the inventory is full, return
     * to the parent village.
     */
    @Override
    public void move() {
        Creature metCreature =
                parentMap.getNearestEnemyWithinDistance(this, 16);

        if (metCreature != null) {
            attackCreature(metCreature);
            return;
        }

        if (onExpedition)
            observeAndFollow();
        else {
            if (Position.squaredDistanceBetween(position,
                    parentVillage.getPosition()) < 64)
                inVillageActions();
            else
                makeMoveTowards(parentVillage.getPosition());
            return;
        }

        if (inventory.isFull())
            onExpedition = false;
        else
            tryCollectResource();
    }

    /**
     * Does nothing as human cannot be resurrected
     *
     * @return null
     */
    @Override
    public Creature resurrect() {
        return null;
    }

    @Override
    public Color getIconColor() {
        return parentVillage.getTeamColor();
    }

    @Override
    public void attack(int damage, int teamID) {
        this.health -= damage;
        if (hasArmor > 0) {
            this.hasArmor--;
            if (hasArmor == 0) {
                maxHealth = humanBasicHealth;
                health = Math.max(health, 100);
            }
        }
        if (!isAlive())
            parentVillage.killVillager(this, teamID);
    }
}
