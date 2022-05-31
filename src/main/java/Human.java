public class Human extends Creature {
    private final int teamID;
    private boolean onExpedition;
    private final Village parentVillage;
    private int positionTriesLeft = 10;
    private int randomTicksLeft = 0;
    private int hasArmor = 0;
    private int hasSword = 0;
    private int armorBonus = 2;
    private int swordBonus = 5;

    public Human(int teamID, Map parentMap, Village parentVillage,
                 Position position) {
        super(parentMap, position, 100, 10, 8, 16);

        this.teamID = teamID;
        this.parentVillage = parentVillage;
    }

    public void equipArmor(){
        this.hasArmor = 8;
        this.health += armorBonus;
    }

    public void equipSword(){
        this.hasSword = 8;
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
            if (parentMap.getBiomeAt(newRandomPosition) !=
                    BiomeConverter.Biome.OCEAN &&
                    (Position.squaredDistanceBetween(newRandomPosition,
                            position) <
                            Position.squaredDistanceBetween(this.position,
                                    position))) {
                this.position = newRandomPosition;
                positionTriesLeft = 10;
            }
            else
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

    @Override
    public void move() {
        Creature metCreature = parentMap.getNearestEnemyWithinDistance(this, 16);

        if (metCreature != null) {
            if(hasSword<=0){
                metCreature.attack(attackStrength, teamID);
            }
            else {
                metCreature.attack(attackStrength + swordBonus, teamID);
                hasSword--;
            }
            if (!metCreature.isAlive()) {
                Inventory killeeInventory = metCreature.takeInventory();
                inventory.append(killeeInventory);
            }
            return;
        }

        if (onExpedition) {
            Creature seenCreature = parentMap.getNearestEnemyWithinDistance(this, 256);
            if(seenCreature != null)
                makeMoveTowards(seenCreature.getPosition());
            else
                super.move();
        }
        else {
            if (Position.squaredDistanceBetween(position, parentVillage.getPosition()) < 64) {
                parentVillage.storeItems(inventory);
                inventory.clear();
                onExpedition = true;
                if(!(hasArmor > 0)){
                    if(parentVillage.getInventory().useItem(new Item(Item.ItemType.ARMOR), 1)){
                        equipArmor();
                    }
                }
                if(!(hasSword > 0)){
                    if(parentVillage.getInventory().useItem(new Item(Item.ItemType.SWORD), 1)){
                        equipSword();
                    }
                }
            } else
                makeMoveTowards(parentVillage.getPosition());
            return;
        }

        if (inventory.isOverflowed()) {
            onExpedition = false;
        } else {
            Item collectedResource = parentMap.collectResource(this);
            if (collectedResource != null)
                inventory.addItem(collectedResource, 1);
        }
    }

    @Override
    public void attack(int damage, int teamID) {
        if(hasArmor <= 0){
            this.health -= damage;
        }
        else {
            this.health -= damage/2;
            this.hasArmor--;
        }
        if (health <= 0) parentVillage.killVillager(this, teamID);
    }
}
