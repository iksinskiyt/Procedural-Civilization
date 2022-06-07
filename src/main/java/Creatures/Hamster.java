package Creatures;

import Simulation.Inventory;
import Terrain.Map;
import Structures.Position;

import java.awt.*;

/**
 * Hamster creature
 */
class Hamster extends Creature {
    /**
     * Construct a new hamster
     *
     * @param parentMap A map were the hamster lives
     * @param position  A hamster's starting position
     */
    Hamster(Map parentMap, Position position) {
        super(parentMap, position, 40, 0, 10, 1);
        inventory.addItem(Inventory.ItemType.MEAT, 1);
    }

    @Override
    public Creature resurrect() {
        return Creature.createNew(CreatureType.HAMSTER, parentMap);
    }

    @Override
    public Color getIconColor() {
        return new Color(0x00ffff);
    }
}
