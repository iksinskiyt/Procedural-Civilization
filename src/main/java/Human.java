import java.util.Random;

public class Human extends Creature {
    private final int teamID;
    private boolean onExpedition;
    private final Village parentVillage;
    private int positionTriesLeft = 10;
    private int randomTicksLeft = 0;
    private static int humanBasicHealth = 100;
    private int hasArmor = 0;
    private int hasSword = 0;
    private int armorBonus = 20;
    private int swordBonus = 5;
    private final Random random;
    private int eatingCounter = 3;
    private int tempEatingCounter = 1;
    private int foodRegenAmount = 10;
    public int maxHealth=humanBasicHealth;
    public int maxArmor = 8;
    public int maxSword = 8;

    public Human(int teamID, Map parentMap, Village parentVillage,
                 Position position) {
        super(parentMap, position, humanBasicHealth, 10, 8, 16);

        this.teamID = teamID;
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

    private void makeMoveTowards(Position position) {
        if (randomTicksLeft > 0) {
            super.move();
            randomTicksLeft--;
        } else {
            Position newRandomPosition = getNewRandomPosition();
            if (Position.squaredDistanceBetween(newRandomPosition,
                    position) <
                    Position.squaredDistanceBetween(this.position,
                            position)) {
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

    public boolean checkArmor(){
        return hasArmor>0;
    }

    public boolean checkSword(){
        return hasSword>0;
    }

    public int getArmor(){
        return hasArmor;
    }

    public int getSword(){
        return hasSword;
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getMaxArmor(){
        return maxArmor;
    }

    public int getMaxSword(){
        return maxSword;
    }

    @Override
    public void move() {
        Creature metCreature =
                parentMap.getNearestEnemyWithinDistance(this, 16);

        if (metCreature != null) {
            int randomFuzz = random.nextInt(5) - 2;
            if (!checkSword()) {
                metCreature.attack(attackStrength + randomFuzz, teamID);
            } else {
                metCreature.attack(attackStrength + swordBonus + randomFuzz,
                        teamID);
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
                } else if (!checkSword()) {
                    if (parentVillage.getInventory()
                            .useItem(Inventory.ItemType.SWORD, 1)) {
                        equipSword();
                    }
                }
                if (checkArmor()) {
                    tempEatingCounter--;
                    if (tempEatingCounter == 0) {
                        tempEatingCounter = eatingCounter;
                        if (health < humanBasicHealth + armorBonus) {
                            if (parentVillage.getInventory()
                                    .useItem(Inventory.ItemType.FOOD, 1)) {
                                health = +foodRegenAmount;
                                if (health > humanBasicHealth + armorBonus)
                                    health = humanBasicHealth + armorBonus;
                            }
                        }
                    }
                } else {
                    tempEatingCounter--;
                    if (tempEatingCounter == 0) {
                        tempEatingCounter = eatingCounter;
                        if (health < humanBasicHealth) {
                            if (parentVillage.getInventory()
                                    .useItem(Inventory.ItemType.FOOD, 1)) {
                                health = +foodRegenAmount;
                                if (health > humanBasicHealth)
                                    health = humanBasicHealth;
                            }
                        }
                    }
                }
            } else
                makeMoveTowards(parentVillage.getPosition());
            return;
        }

        if (inventory.isOverflowed()) {
            onExpedition = false;
        } else {
            Inventory.ItemType collectedResource = parentMap.collectResource(this);
            if (collectedResource != null)
                inventory.addItem(collectedResource, 1);
        }
    }

    @Override
    public void attack(int damage, int teamID) {
        if (!(checkArmor())) {
            this.health -= damage;
        } else {
            this.health -= damage;
            this.hasArmor--;
            if (hasArmor == 0) maxHealth = humanBasicHealth;
            if (hasArmor == 0 && health > 100) health = 100;
            if (health <= 0) parentVillage.killVillager(this, teamID);
        }
        if (health <= 0) parentVillage.killVillager(this, teamID);
    }
}
