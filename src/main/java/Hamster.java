public class Hamster extends Creature {
    public Hamster(Map parentMap, Position position) {
        super(parentMap, position, 4, 0, 10, 0);
        inventory.addItem(new Item(Item.ItemType.FOOD), 1);
    }
}
