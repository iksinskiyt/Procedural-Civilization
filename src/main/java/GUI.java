import org.javatuples.Triplet;

import java.util.List;

public class GUI {
    private Map map;
    private int[] seed;
    private double[] perlinOptions;
    private List<Triplet<Creature.CreatureType, Integer, Integer>> creatures;
    private int nVillages;

    public GUI() {
    }

    public void startSimulation() {
        map = new Map(perlinOptions, creatures, nVillages);
    }

    public void getOptionsFromUser() {
    }

    public void showSimulation() {
        HeightMap heightMap = map.getHeightMap();
        List<Creature> creatures = map.getCreatureList();
        List<Village> villages = map.getVillageList();
        // TODO: Display simulation state using the above parameters
    }

    public void simulationTick() {
        map.simulationTick();
    }

    public boolean isSimulationComplete() {
        return false;
    }
}
