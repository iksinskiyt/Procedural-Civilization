import Structures.SimulationOptions;
import Terrain.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapTest extends Map {
    private static final SimulationOptions simulationOptions;

    static {
        simulationOptions = new SimulationOptions();
        simulationOptions.mapSize = 800;
        simulationOptions.nTeams = 1;
        simulationOptions.teamPopulation = 2;
        simulationOptions.nHamsters = 5;
        simulationOptions.nCows = 4;
        simulationOptions.noiseScale = 1.0;
        simulationOptions.noiseOctaves = 10;
    }

    public MapTest() {
        super(simulationOptions);
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(1, getVillageList().size());
        Assertions.assertEquals(9, getCreatureList().size());

        killCreature(getCreatureList().get(4));
        // Should be resurrected
        Assertions.assertEquals(9, getCreatureList().size());

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            killCreature(getCreatureList().get(10));
        });

        Assertions.assertFalse(isSimulationComplete());
        simulationTick();
        Assertions.assertTrue(isSimulationComplete());
    }
}
