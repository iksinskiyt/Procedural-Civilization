package Simulation;

import java.util.EnumMap;

/**
 * Inventory - item container
 */
public class Inventory {
    /**
     * Maximum number of items allowed to be present in the inventory
     */
    private final int maxCapacity;

    /**
     * Map containing number of items of each type
     */
    private final EnumMap<ItemType, Integer> items;

    /**
     * Item type
     */
    public enum ItemType {
        /**
         * Stone - used to construct buildings and manufacture swords
         */
        STONE,

        /**
         * Wood - used to construct building
         */
        WOOD,

        /**
         * Wheat - used to make food
         */
        WHEAT,

        /**
         * Leather - used to manufacture armor
         */
        LEATHER,

        /**
         * Meat - used to make food
         */
        MEAT,

        /**
         * Food - used to regenerate villagers' health
         */
        FOOD,

        /**
         * Armor - can be equipped by a villager to increase his maximum health
         */
        ARMOR,

        /**
         * Sword - can be equipped by a villager to increase his attack
         * strength
         */
        SWORD
    }

    /**
     * Construct a new inventory
     *
     * @param capacity Maximum number of items allowed to be present in the
     *                 inventory
     */
    public Inventory(int capacity) {
        items = new EnumMap<>(ItemType.class);
        maxCapacity = capacity;
    }

    /**
     * Remove all items from the inventory
     */
    public void clear() {
        items.clear();
    }

    /**
     * Append an inventory to this one
     *
     * @param inventory Inventory to be appended
     */
    public void append(Inventory inventory) {
        for (ItemType key : ItemType.values()) {
            this.addItem(key, inventory.items.getOrDefault(key, 0));
        }
    }

    /**
     * Calculate inventory's free capacity
     *
     * @return How many items can fit to the inventory without overflowing it
     */
    public int freeCapacity() {

        int sum = 0;
        for (int usedCapacity : items.values()) {
            sum += usedCapacity;
        }
        return maxCapacity - sum;
    }

    /**
     * Check if the inventory is full
     *
     * @return Whether the inventory is full
     */
    public boolean isFull() {
        return freeCapacity() <= 0;
    }

    /**
     * Check if the inventory has enough items of give type
     *
     * @param item   Item type
     * @param amount Minimum amount of items
     * @return Whether there is enough items of given type
     */
    public boolean isEnough(ItemType item, int amount) {
        return items.getOrDefault(item, 0) >= amount;
    }

    /**
     * Add an item to the inventory
     *
     * @param item   Item type
     * @param amount Amount of items to add
     */
    public void addItem(ItemType item, int amount) {
        int value =
                items.getOrDefault(item, 0) + Math.min(amount, freeCapacity());
        items.put(item, value);
    }

    /**
     * Remove an item from the inventory
     *
     * @param item   Item type
     * @param amount Amount of items to remove
     */
    private void removeItem(ItemType item, int amount) {
        items.put(item, items.get(item) - amount);
    }

    /**
     * Remove items from the inventory if there is enough of them
     *
     * @param item   Item type
     * @param amount Amount of items to use
     * @return Whether there was enough items to be used
     */
    public boolean useItem(ItemType item, int amount) {
        if (isEnough(item, amount)) {
            removeItem(item, amount);
            return true;
        }
        else
            return false;
    }

    /**
     * Try to use items of many types at once
     *
     * @param items Map containing a number of items of each type
     * @return Whether there was enough items to be used
     */
    public boolean useItems(EnumMap<ItemType, Integer> items) {
        for (ItemType itemType : items.keySet())
            if (!isEnough(itemType, items.get(itemType)))
                return false;
        for (ItemType itemType : items.keySet())
            removeItem(itemType, items.get(itemType));
        return true;
    }
}