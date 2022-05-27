public class Item {
    public enum ItemType {
        STONE,
        BRICK,
        WHEAT,
        LEATHER
    }

    public final ItemType itemType;
    // public int amount;

    public Item(ItemType itemType) {
        this.itemType = itemType;
        // this.amount = amount;
    }
}
