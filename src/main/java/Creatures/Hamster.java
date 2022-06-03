package Creatures;

import Simulation.Inventory;
import Simulation.Map;
import Structures.Position;

public class Hamster extends Creature {
    Hamster(Map parentMap, Position position) {
        super(parentMap, position, 40, 0, 10, 1);
        inventory.addItem(Inventory.ItemType.MEAT, 1);
    }
}
