import org.javatuples.Triplet;

public class Map {
    private Village[] villages;
    private HeightMap heightMap;
    private Creature[] creatures;
    private boolean simulationComplete;

    public Map(double[] perlinOptions, Triplet<Creature.CreatureType, Integer, Integer>[] creatures) {
    }
    public void simulationTick() {}
    public HeightMap getHeightMap() {return heightMap;}
    public Creature[] getCreatureList() {return creatures;}
    public Village[] getVillageList() {return villages;}
    public boolean isSimulationComplete() {return simulationComplete;}
    public void killCreature(Creature creature) {}
    Creature getNearestReachableCreature(int[] position) {return null;}
    Item collectResource(int[] position) {return null;}
}
