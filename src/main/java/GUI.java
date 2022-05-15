import org.javatuples.Triplet;
import java.util.List;

public class GUI {
    private Map map;
    private int[] seed;
    private double[] perlinOptions;
    private List<Triplet<Creature.CreatureType, Integer, Integer>> creatures;
    private int nVillages;

    public GUI() {}
    public void startSimulation() {
        this.map = new Map(perlinOptions, creatures, nVillages);
    }
    public void getOptionsFromUser() {}
    public void showSimulation() {}
    public void simulationTick() {
        this.map.simulationTick();
    }
    public boolean isSimulationComplete() {return false;}
}
