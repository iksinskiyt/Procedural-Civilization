public class Hamster extends Creature {
    public Hamster(Map parentMap, Position position) {
        super(parentMap, position, 4, 0, 10, 1);
        inventory.addItem(Inventory.ItemType.MEAT, 1);
    }
}
