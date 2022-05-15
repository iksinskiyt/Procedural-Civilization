public class Item {
    public enum ItemType {
        STONE,
        BRICK,
        WHEAT,
        LEATHER
    }

    public final ItemType itemType;
    public int amount;

    public Item(ItemType itemType, int amount) {
        this.itemType = itemType;
        this.amount = amount;
    }
}
