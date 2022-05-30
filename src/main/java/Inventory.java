import java.util.HashMap;


public class Inventory {
    private final int maxCapacity;
    private HashMap<Item, Integer> items;


    public Inventory(int capacity) {
        items = new HashMap<>();
        maxCapacity = capacity;
    }

    public void clear() {
        items.clear();
    }

    public void append(Inventory inventory) {
        for (Item key : items.keySet()) {
            this.items.put(key, this.items.get(key) + inventory.items.get(key));
        }
    }

    public int itemAmount(Inventory inventory, Item item){
        int amount = items.get(item);
        return amount;
    }

    public int freeCapacity(){

        int sum =0;
        for (int usedCapacity : items.values()) {
            sum =+ usedCapacity;
        }
        return maxCapacity - sum;
    }

    public boolean isOverflowed() {
        int sum =0;
        for (int usedCapacity : items.values()) {
            sum =+ usedCapacity;
        }
        return sum > maxCapacity;
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