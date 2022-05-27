public class Item {
    public enum ItemType {
        STONE,
        WOOD,
        WHEAT,
        LEATHER
    }
    public final ItemType itemType;

    public Item(ItemType itemType) {
        this.itemType = itemType;
    }
}
