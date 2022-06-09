import Creatures.Human;
import Structures.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HumanTest extends Human {
    public HumanTest() {
        super(new VillageTest(), new Position(300, 350));
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(3, getTeamID());
        Assertions.assertTrue(isAlive());
        Assertions.assertNull(resurrect());

        attack(200, 0);

        Assertions.assertFalse(isAlive());
    }
}
