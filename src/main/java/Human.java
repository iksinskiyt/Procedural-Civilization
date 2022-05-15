import org.javatuples.Pair;

public class Human extends Creature {
    private int teamID;
    private boolean onExpedition;
    private Village parentVillage;

    public Human(int teamID, Map parentMap, Village parentVillage,
                 Pair<Integer, Integer> position) {
        super(CreatureType.HUMAN, parentMap, position);

        this.teamID = teamID;
        this.parentVillage = parentVillage;
    }

    public boolean isOnExpedition() {
        return onExpedition;
    }

    public void goToExpedition() {
    }

    public Village getParentVillage() {
        return parentVillage;
    }

    public int getTeamID() {
        return teamID;
    }

    @Override
    public void move() {
        if (!onExpedition) super.move();
        else {
            if (position == parentVillage.getPosition()) {
                parentVillage.storeItems(inventory);
                inventory.clear();
                onExpedition = true;
            } else {
                //TODO: Make a move towards the village
            }
        }

        Creature metCreature = parentMap.getNearestReachableCreature(position);

        if ((metCreature != null) &&
                (metCreature.getType() != CreatureType.HUMAN ||
                        (metCreature.getType() == CreatureType.HUMAN &&
                                ((Human) metCreature).getTeamID() == teamID))) {
            metCreature.attack(attackStrength);
            if (metCreature.getHealth() <= 0) {
                Inventory killeeInventory = metCreature.takeInventory();
                inventory.append(killeeInventory);
                if (metCreature.getType() != CreatureType.HUMAN) {
                    parentMap.killCreature(metCreature);
                } else {
                    Human metHuman = (Human) metCreature;
                    Village mhParentVillage = metHuman.getParentVillage();
                    mhParentVillage.killVillager(metHuman, teamID);
                }
            }

            if (inventory.getItemAmountSum() > inventory.getCapacity()) {
                onExpedition = false;
            } else {
                Item collectedResource = parentMap.collectResource(position);
                inventory.addItem(collectedResource);
            }
        }
    }
}
