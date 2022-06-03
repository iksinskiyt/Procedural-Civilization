package Simulation;

import java.util.EnumMap;

public class Inventory {
    private final int maxCapacity;
    private final EnumMap<ItemType, Integer> items;

    public enum ItemType {
        STONE, WOOD, WHEAT, LEATHER, MEAT, FOOD, ARMOR, SWORD
    }

    public Inventory(int capacity) {
        items = new EnumMap<>(ItemType.class);
        maxCapacity = capacity;
    }

    public void clear() {
        items.clear();
    }

    public void append(Inventory inventory) {
        for (ItemType key : ItemType.values()) {
            this.addItem(key, inventory.items.getOrDefault(key, 0));
        }
    }

    public int freeCapacity() {

        int sum = 0;
        for (int usedCapacity : items.values()) {
            sum += usedCapacity;
        }
        return maxCapacity - sum;
    }

    public boolean isFull() {
        return freeCapacity() <= 0;
    }

    public boolean isEnough(ItemType item, int amount) {
        return items.getOrDefault(item, 0) >= amount;
    }

    public void addItem(ItemType item, int amount) {
        int value =
                items.getOrDefault(item, 0) + Math.min(amount, freeCapacity());
        items.put(item, value);
    }

    private void removeItem(ItemType item, int amount)
    {
        items.put(item, items.get(item) - amount);
    }

    public boolean useItem(ItemType item, int amount) {
        if (isEnough(item, amount)) {
            removeItem(item, amount);
            return true;
        } else
            return false;
    }

    public boolean useItems(EnumMap<ItemType, Integer> items)
    {
        for(ItemType itemType : items.keySet())
            if(!isEnough(itemType, items.get(itemType)))
                return false;
        for(ItemType itemType : items.keySet())
            removeItem(itemType, items.get(itemType));
        return true;
    }
}