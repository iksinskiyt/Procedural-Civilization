public class Inventory {
    private int equipment[];
    private final int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    public void clear() {
    }

    public void append(Inventory inventory) {
    }

    public boolean isOverflowed() {
        int sum = 0;
        for (int amount : equipment)
            sum += amount;
        return sum > capacity;
    }

    public void addItem(Item item) {
        equipment[item.itemType.ordinal()] =+ item.amount;
    }
}
