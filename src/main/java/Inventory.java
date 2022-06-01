import java.util.EnumMap;


public class Inventory {
    private final int maxCapacity;
    private EnumMap<ItemType, Integer> items;

    public enum ItemType {
        STONE,
        WOOD,
        WHEAT,
        LEATHER,
        MEAT,
        FOOD,
        ARMOR,
        SWORD
    }
    public static ItemType itemType;

    public Inventory(int capacity) {
        items = new EnumMap<>(ItemType.class);
        maxCapacity = capacity;
    }

    public void clear() {
        items.clear();
    }

    public void append(Inventory inventory) {
        for (ItemType key : items.keySet()) {
            this.items.put(key, this.items.get(key) + inventory.items.get(key));
        }
    }

    public int itemAmount(Inventory inventory, ItemType item){
        return items.get(item);
    }

    public int freeCapacity(){

        int sum =0;
        for (int usedCapacity : items.values()) {
            sum += usedCapacity;
        }
        return maxCapacity - sum;
    }

    public boolean isOverflowed() {
        return freeCapacity() < 0;
    }

    public boolean isEnough(Item item, int amount){
        return items.getOrDefault(item, 0) >= amount;
    }

    public void addItem(Item item, int amount) {
            if(freeCapacity()>amount){
            int value = items.getOrDefault(item, 0) + amount;
            items.put(item,value);
        }
    }

    public boolean useItem(Item item, int amount){
        if (isEnough(item, amount)){
            int value = items.get(item) - amount;
            items.put(item,value);
            return true;
        }
        else return false;       
    }
}