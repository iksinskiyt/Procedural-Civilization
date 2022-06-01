public class House extends Building {
    
    public static final int houseWoodCost = 25; // changable house cost variable
    private final int sexCounter = 420;
    private int tempSexCounter = sexCounter;

    public House(Village parentVillage){
        super(parentVillage);
    }

    @Override
    public void simulationTick(){
        if(tempSexCounter-- == 0){
            tempSexCounter = sexCounter;
            Human human = new Human(parentVillage.getTeamID(), parentVillage.getParentMap(), parentVillage, parentVillage.getPosition());
            parentVillage.addVillager(human);
        }
    }
}