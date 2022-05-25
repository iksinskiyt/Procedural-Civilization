import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private final int capacity;

    public Inventory(int capacity) {
        items = new ArrayList<>();
        this.capacity = capacity;
    }

    public void clear() {
    }

    public void append(Inventory inventory) {
    }

    public boolean isOverflowed() {
        int sum = 0;
        for (Item item : items)
            sum += item.amount;
        return sum > capacity;
    }

    public void addItem(Item item) {
    }
}
