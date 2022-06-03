package Buildings;

import Creatures.Creature;
import Creatures.Human;
import Simulation.Village;

public class House extends Building {

    public static final int houseWoodCost = 25; // changable house cost variable
    private final int sexCounter = 420;
    private int tempSexCounter = sexCounter;

    public House(Village parentVillage) {
        super(parentVillage);
    }

    @Override
    public void simulationTick() {
        if (tempSexCounter-- == 0) {
            tempSexCounter = sexCounter;
            parentVillage.addVillager(Human.createNew(parentVillage,
                    parentVillage.getPosition()));
        }
    }
}