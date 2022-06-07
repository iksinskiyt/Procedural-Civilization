package Creatures;

import Simulation.Inventory;
import Structures.Position;
import Terrain.Map;

import java.awt.*;

/**
 * Cow creature
 */
class Cow extends Creature {
    /**
     * Construct a new cow
     *
     * @param parentMap A map were the cow lives
     * @param position  A cow's starting position
     */
    Cow(Map parentMap, Position position) {
        super(parentMap, position, 50, 0, 2, 6);
        inventory.addItem(Inventory.ItemType.MEAT, 4);
        inventory.addItem(Inventory.ItemType.LEATHER, 2);
    }

    @Override
    public Creature resurrect() {
        return Creature.createNew(CreatureType.COW, parentMap);
    }

    @Override
    public Color getIconColor() {
        return new Color(0xffff00);
    }
}
