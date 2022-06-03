package Creatures;

import Simulation.Inventory;
import Simulation.Map;
import Simulation.Village;
import Structures.Position;

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

    Human(Map parentMap, Position position, Village parentVillage) {
        super(parentMap, position, humanBasicHealth, 10, 8, 16);

        this.parentVillage = parentVillage;
        random = new Random();
    }

    public void equipArmor() {
        this.hasArmor = maxArmor;
        this.health += armorBonus;
        maxHealth += armorBonus;
    }

    public void equipSword() {
        this.hasSword = maxSword;
    }

    public int getTeamID() {
        return parentVillage.getTeamID();
    }

    private void makeMoveTowards(Position position) {
        if (randomTicksLeft > 0) {
            super.move();
            randomTicksLeft--;
        } else {
            Position newRandomPosition = getNewRandomPosition();
            if (Position.squaredDistanceBetween(newRandomPosition, position) <
                    Position.squaredDistanceBetween(this.position, position)) {
                this.position = newRandomPosition;
                positionTriesLeft = 10;
            } else
                positionTriesLeft--;
        }
        if (positionTriesLeft <= 0) {
            if (randomTicksLeft <= 0) {
                randomTicksLeft = 100;
                positionTriesLeft = 10;
            } else
                randomTicksLeft--;
        }
    }

    public boolean checkArmor() {
        return hasArmor > 0;
    }

    public boolean checkSword() {
        return hasSword > 0;
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

    @Override
    public void move() {
        Creature metCreature =
                parentMap.getNearestEnemyWithinDistance(this, 16);

        if (metCreature != null) {
            int randomFuzz = random.nextInt(5) - 2;
            if (!checkSword()) {
                metCreature.attack(attackStrength + randomFuzz,
                        parentVillage.getTeamID());
            } else {
                metCreature.attack(attackStrength + swordBonus + randomFuzz,
                        parentVillage.getTeamID());
                hasSword--;
            }
            if (!metCreature.isAlive()) {
                Inventory killeeInventory = metCreature.takeInventory();
                inventory.append(killeeInventory);
            }
            return;
        }

        if (onExpedition) {
            Creature seenCreature =
                    parentMap.getNearestEnemyWithinDistance(this, 256);
            if (seenCreature != null)
                makeMoveTowards(seenCreature.getPosition());
            else
                super.move();
        } else {
            if (Position.squaredDistanceBetween(position,
                    parentVillage.getPosition()) < 64) {
                parentVillage.storeItems(inventory);
                inventory.clear();
                onExpedition = true;
                if (!(checkArmor())) {
                    if (parentVillage.getInventory()
                            .useItem(Inventory.ItemType.ARMOR, 1)) {
                        equipArmor();
                    }
                }
                if (!checkSword()) {
                    if (parentVillage.getInventory()
                            .useItem(Inventory.ItemType.SWORD, 1)) {
                        equipSword();
                    }
                }
                tempEatingCounter--;
                if (tempEatingCounter == 0) {
                    tempEatingCounter = eatingCounter;
                    if (health < humanBasicHealth +
                            (checkArmor() ? armorBonus : 0)) {
                        if (parentVillage.getInventory()
                                .useItem(Inventory.ItemType.FOOD, 1)) {
                            health += foodRegenAmount;
                            health = Math.min(health, humanBasicHealth +
                                    (checkArmor() ? armorBonus : 0));
                        }
                    }
                }
            } else
                makeMoveTowards(parentVillage.getPosition());
            return;
        }

        if (inventory.isFull()) {
            onExpedition = false;
        } else {
            Inventory.ItemType collectedResource =
                    parentMap.collectResource(this);
            if (collectedResource != null)
                inventory.addItem(collectedResource, 1);
        }
    }

    @Override
    public void attack(int damage, int teamID) {
        this.health -= damage;
        if (checkArmor()) {
            this.hasArmor--;
            if (hasArmor == 0) {
                maxHealth = humanBasicHealth;
                health = Math.max(health, 100);
            }
        }
        if (health <= 0)
            parentVillage.killVillager(this, teamID);
    }
}
