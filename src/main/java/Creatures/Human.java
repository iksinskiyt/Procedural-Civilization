package Creatures;

import Simulation.Inventory;
import Simulation.Village;
import Structures.Position;

import java.awt.*;
import java.util.Random;

public class Human extends Creature {
    private boolean onExpedition;
    private final Village parentVillage;
    private int positionTriesLeft = 10;
    private int randomTicksLeft = 0;
    private static final int humanBasicHealth = 100;
    private int hasArmor = 0;
    private int hasSword = 0;
    private final int armorBonus = 20;
    private final int swordBonus = 5;
    private final Random random;
    private final int eatingCounter = 3;
    private int tempEatingCounter = 1;
    private final int foodRegenAmount = 10;
    public int maxHealth = humanBasicHealth;
    public int maxArmor = 8;
    public int maxSword = 8;

    Human(Village parentVillage, Position position) {
        super(parentVillage.getParentMap(), position, humanBasicHealth, 10, 8,
                16);

        this.parentVillage = parentVillage;
        random = new Random();
    }

    public static Human createNew(Village parentVillage, Position position) {
        return new Human(parentVillage, position);
    }

    private void tryEquipArmor() {
        if (parentVillage.getInventory().useItem(Inventory.ItemType.ARMOR, 1)) {
            this.hasArmor = maxArmor;
            this.health += armorBonus;
            maxHealth += armorBonus;
        }
    }

    public void tryEquipSword() {
        if (parentVillage.getInventory().useItem(Inventory.ItemType.SWORD, 1)) {
            this.hasSword = maxSword;
        }
    }

    @Override
    public int getTeamID() {
        return parentVillage.getTeamID();
    }

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

    public int getArmor() {
        return hasArmor;
    }

    public int getSword() {
        return hasSword;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxArmor() {
        return maxArmor;
    }

    public int getMaxSword() {
        return maxSword;
    }

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

    private void observeAndFollow() {
        Creature seenCreature =
                parentMap.getNearestEnemyWithinDistance(this, 256);
        if (seenCreature != null)
            makeMoveTowards(seenCreature.getPosition());
        else
            super.move();
    }

    private void tryEatFood() {
        if (health < humanBasicHealth + (hasArmor > 0 ? armorBonus : 0) &&
                parentVillage.getInventory()
                        .useItem(Inventory.ItemType.FOOD, 1)) {
            health += foodRegenAmount;
            health = Math.min(health,
                    humanBasicHealth + (hasArmor > 0 ? armorBonus : 0));
        }
    }

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

    private void tryCollectResource() {
        Inventory.ItemType collectedResource = parentMap.collectResource(this);
        if (collectedResource != null)
            inventory.addItem(collectedResource, 1);
    }

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

    @Override
    public Creature resurrect() {
        // A human can not be resurrected
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
