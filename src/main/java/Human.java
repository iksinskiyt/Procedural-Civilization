import org.javatuples.Pair;

public class Human extends Creature {
    private int teamID;
    private boolean onExpedition;
    private Village parentVillage;

    public Human(int teamID, Map parentMap, Village parentVillage, Pair<Integer, Integer> position) {
        super(CreatureType.HUMAN, parentMap, position);

        this.teamID = teamID;
        this.parentVillage = parentVillage;
    }
    public boolean isOnExpedition() {return onExpedition;}
    public void goToExpedition() {}
    public Village getParentVillage() {return parentVillage;}
}
