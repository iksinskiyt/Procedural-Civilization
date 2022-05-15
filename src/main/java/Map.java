import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class Map {
    private List<Village> villages;
    private HeightMap heightMap;
    private List<Creature> creatures;
    private boolean simulationComplete;

    public Map(double[] perlinOptions,
               List<Triplet<Creature.CreatureType, Integer, Integer>> creatures,
               int nVillages) {
        Perlin perlin = new Perlin(perlinOptions);
        heightMap = new HeightMap();
        // TODO: Perlin generation

        villages = new ArrayList<>();

        for (int i = 0; i < nVillages; i++) {
            // TODO: Replace with real random position generation
            Pair<Integer, Integer> randomPosition =
                    new Pair<Integer, Integer>(0, 0);

            Village village = new Village(randomPosition, i, this);
            villages.add(village);
        }

        this.creatures = new ArrayList<>();

        for (Triplet<Creature.CreatureType, Integer, Integer> creature : creatures) {
            for (int i = 0; i < creature.getValue1(); i++) {
                for (int j = 0; j < creature.getValue2(); j++) {
                    // TODO: Replace with real random position generation
                    Pair<Integer, Integer> randomPosition =
                            new Pair<Integer, Integer>(0, 0);

                    if (creature.getValue0() == Creature.CreatureType.HUMAN) {
                        Human humanObject =
                                new Human(i, this, villages.get(i),
                                        randomPosition);
                        villages.get(i).addVillager(humanObject);
                    } else {
                        Creature creatureObject =
                                new Creature(creature.getValue0(), this,
                                        randomPosition);
                        this.creatures.add(creatureObject);
                    }
                }
            }
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

    Creature getNearestReachableCreature(Pair<Integer, Integer> position) {
        return null;
    }

    Item collectResource(Pair<Integer, Integer> position) {
        return null;
    }
}
