import java.util.List;
import java.util.ArrayList;

public class Map {
    public enum Biome {
        OCEAN,
        PLAINS,
        FOREST,
        MOUNTAINS
    }

    private final List<Village> villages;
    private final HeightMap heightMap;
    private final List<Creature> creatures;
    private boolean simulationComplete;

    private Position getRandomPosition(
            List<Biome> allowedBiomes) {
        // TODO: Replace with real random position generation
        return new Position(0, 0);
    }

    public Map(PerlinOptions perlinOptions,
               SimulationOptions simulationOptions) {
        Perlin perlin = new Perlin(perlinOptions);
        heightMap = new HeightMap();
        // TODO: Perlin generation

        villages = new ArrayList<>();

        for (int i = 0; i < simulationOptions.nTeams; i++) {
            Village village =
                    new Village(getRandomPosition(List.of(Biome.PLAINS)), i,
                            this);
            villages.add(village);

            for (int j = 0; j < simulationOptions.teamPopulation; j++) {
                village.addVillager(new Human(i, this, village,
                        getRandomPosition(
                                List.of(Biome.PLAINS, Biome.FOREST))));
            }
        }

        this.creatures = new ArrayList<>();

        for (int i = 0; i < simulationOptions.nCows; i++) {
            creatures.add(
                    new Cow(this, getRandomPosition(List.of(Biome.PLAINS))));
        }

        for (int i = 0; i < simulationOptions.nHamsters; i++) {
            creatures.add(new Hamster(this,
                    getRandomPosition(List.of(Biome.MOUNTAINS))));
        }
    }

    public void simulationTick() {
        int lastTeamID = -1;
        simulationComplete = true;
        for (Village village : villages) {
            int teamID = village.getTeamID();
            if (lastTeamID != -1 && teamID != lastTeamID) {
                simulationComplete = false;
                break;
            }
            lastTeamID = teamID;
        }
        if (simulationComplete)
            return;

        for (Creature creature : creatures)
            creature.move();

        for (Village village : villages)
            village.simulationTick();
    }

    public HeightMap getHeightMap() {
        return heightMap;
    }

    public List<Creature> getCreatureList() {
        return creatures;
    }

    public List<Village> getVillageList() {
        return villages;
    }

    public boolean isSimulationComplete() {
        return simulationComplete;
    }

    public void killCreature(Creature creature) {
        creatures.remove(creature);
    }

    Creature getNearestAttackableCreature(Human requester) {
        // TODO: implementation
        return null;
    }

    Item collectResource(Human requester) {
        // TODO: implementation
        return null;
    }
}
