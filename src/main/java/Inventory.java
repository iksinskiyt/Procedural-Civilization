public class Inventory {
    private Item[] items;
    private final int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    public void manage(Item item, int amount) {
    }

    public void check(Item item) {
    }

    public void clear() {
    }

    public void append(Inventory inventory) {
    }

    public int getItemAmountSum() {
        int sum = 0;
        for (Item item : items)
            sum += item.amount;
        return sum;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addItem(Item item) {
    }
}
