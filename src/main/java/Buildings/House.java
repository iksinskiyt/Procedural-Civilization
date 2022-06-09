package Buildings;

import Simulation.Village;

/**
 * House building
 */
class House extends Building {
    /**
     * How many ticks does it take for a new human to be born
     */
    private final int sexCounter = 420;

    /**
     * A variable used to remember how many ticks the house has to wait before
     * taking any action
     */
    private int tempSexCounter = sexCounter;

    /**
     * Construct a new house
     *
     * @param parentVillage A village where the house is located
     */
    House(Village parentVillage) {
        super(parentVillage);
    }

    @Override
    public void simulationTick() {
        if (tempSexCounter-- == 0) {
            tempSexCounter = sexCounter;
            parentVillage.addVillager();
        }
    }
}