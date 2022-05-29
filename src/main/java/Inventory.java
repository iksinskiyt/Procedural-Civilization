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

    public void append(Inventory inventoryOne, Inventory inventoryTwo) {
        // tba
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
        return items.get(item) >= amount;
    }

    public void addItem(Item item, int amount) {
            if(freeCapacity()>amount){
            int value = items.get(item) + amount;
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