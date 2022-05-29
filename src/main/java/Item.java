public class Item {
    public enum ItemType {
        STONE,
        WOOD,
        WHEAT,
        LEATHER,
        MEAT,
        FOOD
    }
    public final ItemType itemType;

    public Item(ItemType itemType) {
        this.itemType = itemType;
    }
}
