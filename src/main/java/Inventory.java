import java.util.ArrayList;
import java.util.List;

import Item.ItemType;

import java.util.Collections;

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

    public void listInventory(Inventory inventory, ItemType item){
        Collections.frequency(inventory, new Item(item))
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
