package Creatures;

import Simulation.Inventory;
import Simulation.Map;
import Structures.Position;

public class Cow extends Creature {
    Cow(Map parentMap, Position position) {
        super(parentMap, position, 50, 0, 2, 6);
        inventory.addItem(Inventory.ItemType.MEAT, 4);
        inventory.addItem(Inventory.ItemType.LEATHER, 2);
    }
}
