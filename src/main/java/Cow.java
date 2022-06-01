public class Cow extends Creature {
    public Cow(Map parentMap, Position position) {
        super(parentMap, position, 20, 0, 2, 6);
        inventory.addItem(Inventory.ItemType.MEAT, 4);
        inventory.addItem(Inventory.ItemType.LEATHER, 2);
    }
}
