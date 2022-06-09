import Simulation.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InventoryTest extends Inventory {
    public InventoryTest() {
        super(64);
    }

    @Test
    public void addTest() {
        Assertions.assertFalse(isFull());
        Assertions.assertFalse(isEnough(ItemType.WOOD, 4));

        addItem(ItemType.WOOD, 5);
        Assertions.assertTrue(isEnough(ItemType.WOOD, 4));

        addItem(ItemType.STONE, 80);
        Assertions.assertTrue(isFull());
        Assertions.assertFalse(isEnough(ItemType.STONE, 60));

        clear();
        Assertions.assertEquals(64, freeCapacity());
    }
}
