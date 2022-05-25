public class Cow extends Creature {
    public Cow(Map parentMap, Position position) {
        super(parentMap, position, 20, 0, 2, 0);
        inventory.addItem(new Item(Item.ItemType.FOOD, 4));
    }
}
