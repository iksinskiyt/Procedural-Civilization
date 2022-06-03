package Simulation;

import Creatures.Creature;
import Creatures.Human;
import Structures.HeightMap;
import Structures.Position;
import Structures.SimulationOptions;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private final List<Village> villages;
    private final HeightMap heightMap;
    private final List<Creature> creatures;
    private boolean simulationComplete;
    private final SimulationOptions simulationOptions;
    private final Random random;
    private int tickCounter = 0;

    public BiomeConverter.Biome getBiomeAt(Position position) {
        return BiomeConverter.getBiome(
                heightMap.height[position.x][position.y]);
    }

    public Position getRandomPosition(
            List<BiomeConverter.Biome> allowedBiomes) {
        Position position;
        while (true) {
            position = new Position(random.nextInt(simulationOptions.mapSize),
                    random.nextInt(simulationOptions.mapSize));
            BiomeConverter.Biome posBiome = getBiomeAt(position);
            for (BiomeConverter.Biome allowedBiome : allowedBiomes) {
                if (posBiome == allowedBiome)
                    return position;
            }
        }
    }

    public Map(SimulationOptions simulationOptions) {
        this.simulationOptions = simulationOptions;
        random = new Random();
        Perlin perlin = new Perlin(simulationOptions);
        heightMap = new HeightMap();
        heightMap.height = perlin.generatePerlinNoise2D();

        villages = new ArrayList<>();

        for (int i = 0; i < simulationOptions.nTeams; i++) {
            Position newVillagePosition =
                    getRandomPosition(List.of(BiomeConverter.Biome.PLAINS));
            Village village = new Village(newVillagePosition, i, this);
            villages.add(village);

            for (int j = 0; j < simulationOptions.teamPopulation; j++) {
                village.addVillager();
            }
        }

        this.creatures = new ArrayList<>();

        for (int i = 0; i < simulationOptions.nCows; i++) {
            creatures.add(Creature.createNew(Creature.CreatureType.COW, this));
        }

        for (int i = 0; i < simulationOptions.nHamsters; i++) {
            creatures.add(Creature.createNew(Creature.CreatureType.HAMSTER, this));
        }
    }

    public void simulationTick() {
        tickCounter++;
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
        creatures.add(creature.resurrect());
    }

    public Creature getNearestEnemyWithinDistance(Human requester,
                                                  int distance) {
        HashMap<Creature, Integer> creatureDistances = new HashMap<>();
        for (Creature creature : creatures)
            creatureDistances.put(creature,
                    Position.squaredDistanceBetween(requester.getPosition(),
                            creature.getPosition()));
        for (Village village : villages)
            for (Human human : village.getVillagers())
                creatureDistances.put(human,
                        Position.squaredDistanceBetween(requester.getPosition(),
                                human.getPosition()));
        List<java.util.Map.Entry<Creature, Integer>> creatureDistancesSorted =
                new ArrayList<>(creatureDistances.entrySet());
        creatureDistancesSorted.sort(java.util.Map.Entry.comparingByValue());
        for (java.util.Map.Entry<Creature, Integer> creatureDistance : creatureDistancesSorted) {
            if (creatureDistance.getKey().getTeamID() ==
                            requester.getTeamID())
                continue;
            if (creatureDistance.getValue() > distance * distance)
                return null;
            return creatureDistance.getKey();
        }
        return null;
    }

    public Inventory.ItemType collectResource(Human requester) {
        int randomNumber = random.nextInt(1000);
        if (getBiomeAt(requester.getPosition()) ==
                BiomeConverter.Biome.PLAINS) {
            if (randomNumber > 990) {
                return Inventory.ItemType.WOOD;
            } else if (randomNumber < 4) {
                return Inventory.ItemType.STONE;
            } else if (5 < randomNumber && randomNumber < 12) {
                return Inventory.ItemType.WHEAT;
            } else
                return null;
        } else if (getBiomeAt(requester.getPosition()) ==
                BiomeConverter.Biome.MOUNTAINS) {
            if (randomNumber > 990) {
                return Inventory.ItemType.STONE;
            } else
                return null;
        } else {
            if (randomNumber == 69) {
                return Inventory.ItemType.SWORD;
            } else if (randomNumber == 420) {
                return Inventory.ItemType.ARMOR;
            } else
                return null;
        }
    }

    public int getMapSize() {
        return simulationOptions.mapSize;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public int getTeamCount() {
        return simulationOptions.nTeams;
    }
}
