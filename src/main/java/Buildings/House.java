package Buildings;

import Creatures.Human;
import Simulation.Village;

class House extends Building {
    private final int sexCounter = 420;
    private int tempSexCounter = sexCounter;

    House(Village parentVillage) {
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