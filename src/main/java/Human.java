public class Human extends Creature {
    private final int teamID;
    private boolean onExpedition;
    private final Village parentVillage;
    private static int humanBasicHealth = 100;
    private int hasArmor = 0;
    private int hasSword = 0;
    private int armorBonus = 20;
    private int swordBonus = 5;
    private int positionTriesLeft = 10;
    private int randomTicksLeft = 0;
    private int eatingCounter = 3;
    private int tempEatingCounter = 1;
    private int foodRegenAmount = 10;

    public Human(int teamID, Map parentMap, Village parentVillage,
                 Position position) {
        super(parentMap, position, humanBasicHealth, 10, 8, 16);

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

    @Override
    public void move() {
        if (onExpedition) super.move();
        else {
            if (position == parentVillage.getPosition()) {
                parentVillage.storeItems(inventory);
                inventory.clear();
                onExpedition = true;
                if(!(hasArmor > 0)){
                    if(parentVillage.getInventory().useItem(new Item(Item.ItemType.ARMOR), 1)){
                        equipArmor();
                    }
                }
                else if(!(hasSword > 0)){
                    if(parentVillage.getInventory().useItem(new Item(Item.ItemType.SWORD), 1)){
                        equipSword();
                    }
                }
                if(hasArmor>0){// make counter that checks how long ago did the human eat
                    tempEatingCounter--;
                    if(tempEatingCounter==0){
                        tempEatingCounter = eatingCounter;
                        if(health < humanBasicHealth+armorBonus){
                            if(parentVillage.getInventory().useItem(new Item(Item.ItemType.FOOD), 1)){
                                health =+ foodRegenAmount;
                                if(health>humanBasicHealth+armorBonus) health = humanBasicHealth+armorBonus;
                            }
                        }
                    }
                }
                else if(!(hasArmor>0)){
                    tempEatingCounter--;
                    if(tempEatingCounter==0){
                        tempEatingCounter = eatingCounter;
                        if(health<humanBasicHealth){
                            if(parentVillage.getInventory().useItem(new Item(Item.ItemType.FOOD), 1)){
                                health =+ foodRegenAmount;
                                if(health>humanBasicHealth) health = humanBasicHealth;
                            }
                        }
                    }
                }
            } else {
                if (randomTicksLeft > 0) {
                    super.move();
                    randomTicksLeft--;
                } else {
                    Position newRandomPosition = getNewRandomPosition();
                    if (parentMap.getBiomeAt(newRandomPosition) !=
                            BiomeConverter.Biome.OCEAN &&
                            (Position.squaredDistanceBetween(newRandomPosition,
                                    parentVillage.getPosition()) <
                                    Position.squaredDistanceBetween(position,
                                            parentVillage.getPosition()))) {
                        position = newRandomPosition;
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
        }

        Creature metCreature = parentMap.getNearestAttackableCreature(this);

        if (metCreature != null) {
            if(!(hasSword>0)){
                metCreature.attack(attackStrength, teamID);
            }
            else if(hasSword>0){
                metCreature.attack(attackStrength + swordBonus, teamID);
                hasSword--;
            } 
            if (!metCreature.isAlive()) {
                Inventory killeeInventory = metCreature.takeInventory();
                inventory.append(killeeInventory);
                // method that creates a new creature, same type as killed
            }
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
        if(!(hasArmor > 0)){
            this.health -= damage;
            if (health <= 0) parentVillage.killVillager(this, teamID);
        }
        else if(hasArmor > 0){
            this.health -= damage;
            this.hasArmor--;
            if(hasArmor == 0 && health > 100) health = 100;
            if (health <= 0) parentVillage.killVillager(this, teamID);
        }
    }
}
