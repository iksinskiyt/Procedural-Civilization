import Simulation.Village;
import Structures.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VillageTest extends Village {
    VillageTest() {
        super(new Position(200, 300), 3, new MapTest());
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(0, getVillagers().size());
        Assertions.assertEquals(3, getTeamID());

        addVillager();
        addVillager();
        addVillager();
        addVillager();

        Assertions.assertEquals(4, getVillagers().size());

        killVillager(getVillagers().get(0), 4);
        killVillager(getVillagers().get(1), 2);
        killVillager(getVillagers().get(2), 1);
        killVillager(getVillagers().get(3), 4);

        Assertions.assertEquals(4, getVillagers().size());

        simulationTick();

        Assertions.assertEquals(0, getVillagers().size());

        simulationTick();

        Assertions.assertEquals(4, getTeamID());
    }
}
