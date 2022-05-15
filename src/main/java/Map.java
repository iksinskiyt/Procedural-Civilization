import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class Map {
    private List<Village> villages;
    private HeightMap heightMap;
    private List<Creature> creatures;
    private boolean simulationComplete;

    public Map(double[] perlinOptions, List<Triplet<Creature.CreatureType, Integer, Integer>> creatures, int nVillages) {
        this.heightMap = new HeightMap();
        // TODO: Perlin generation

        this.villages = new ArrayList<>();

        for (int i = 0; i < nVillages; i++) {
            // TODO: Replace with real random position generation
            Pair<Integer, Integer> randomPosition = new Pair<Integer, Integer>(0, 0);

            Village village = new Village(randomPosition, i, this);
            this.villages.add(village);
        }

        this.creatures = new ArrayList<>();

        for (Triplet<Creature.CreatureType, Integer, Integer> creature : creatures) {
            for (int i = 0; i < creature.getValue1(); i++) {
                for (int j = 0; j < creature.getValue2(); j++) {
                    // TODO: Replace with real random position generation
                    Pair<Integer, Integer> randomPosition = new Pair<Integer, Integer>(0, 0);

                    if (creature.getValue0() == Creature.CreatureType.HUMAN) {
                        Human humanObject = new Human(i, this, this.villages.get(i), randomPosition);
                        this.villages.get(i).addVillager(humanObject);
                    } else {
                        Creature creatureObject = new Creature(creature.getValue0(), this, randomPosition);
                        this.creatures.add(creatureObject);
                    }
                }
            }
        }
    }

    public void simulationTick() {
        int lastTeamID = -1;
        this.simulationComplete = true;
        for(Village village: this.villages)
        {
            int teamID = village.getTeamID();
            if(lastTeamID != -1 && teamID != lastTeamID) {
                this.simulationComplete = false;
                break;
            }
            lastTeamID = teamID;
        }
        if(this.simulationComplete)
            return;

        for(Creature creature : this.creatures)
            creature.move();
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
    }

    Creature getNearestReachableCreature(int[] position) {
        return null;
    }

    Item collectResource(int[] position) {
        return null;
    }
}
