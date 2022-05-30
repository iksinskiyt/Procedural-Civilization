public class Human extends Creature {
    private final int teamID;
    private boolean onExpedition;
    private final Village parentVillage;
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
                if(!(hasSword > 0)){
                    if(parentVillage.getInventory().useItem(new Item(Item.ItemType.SWORD), 1)){
                        equipSword();
                    }
                }
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
            if(hasSword<0){
                metCreature.attack(attackStrength, teamID);
            }
            if(hasSword>0){
                metCreature.attack(attackStrength + swordBonus, teamID);
                hasSword--;
            } 
            if (!metCreature.isAlive()) {
                Inventory killeeInventory = metCreature.takeInventory();
                inventory.append(killeeInventory);
            }
        }

        if (inventory.isOverflowed()) {
            onExpedition = false;
        } else {
            Item collectedResource = parentMap.collectResource(this);
            if(collectedResource != null)
                inventory.addItem(collectedResource, 1);
        }
    }

    @Override
    public void attack(int damage, int teamID) {
        if(hasArmor < 0){
            this.health -= damage;
            if (health <= 0) parentVillage.killVillager(this, teamID);
        }
        if(hasArmor > 0){
            this.health -= damage;
            this.hasArmor--;
            if (health <= 0) parentVillage.killVillager(this, teamID);
        }
    }
}
