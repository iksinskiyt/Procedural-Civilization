package Creatures;

import Simulation.Inventory;
import Simulation.Map;
import Structures.Position;

import java.awt.*;

class Hamster extends Creature {
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
