package Creatures;

import Simulation.Inventory;
import Simulation.Map;
import Structures.Position;

import java.awt.*;

class Cow extends Creature {
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
