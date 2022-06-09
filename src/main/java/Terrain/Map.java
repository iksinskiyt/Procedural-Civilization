package Terrain;

import Creatures.Creature;
import Creatures.Human;
import Simulation.Inventory;
import Simulation.Village;
import Structures.HeightMap;
import Structures.Position;
import Structures.SimulationOptions;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * World map
 */
public class Map {
    /**
     * List of villages
     */
    private final List<Village> villages;

    /**
     * World height map
     */
    private final HeightMap heightMap;

    /**
     * List of creatures
     */
    private final List<Creature> creatures;

    /**
     * Whether the simulation is complete
     */
    private boolean simulationComplete;

    /**
     * Simulation options provided by the user
     */
    private final SimulationOptions simulationOptions;

    /**
     * Random number generator object
     */
    private final Random random;

    /**
     * Simulation tick counter
     */
    private int tickCounter = 0;

    /**
     * Get biome at the given position
     *
     * @param position Position to determine biome at
     * @return Biome at the given position
     */
    public BiomeConverter.Biome getBiomeAt(Position position) {
        return BiomeConverter.getBiome(
                heightMap.height[position.x][position.y]);
    }

    /**
     * Get a random position within specified biome constraints
     *
     * @param allowedBiomes List of biomes where the new position is allowed to
     *                      be
     * @return Position object
     */
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

    /**
     * Construct a new map. Generate the world height map using Perlin noise
     * generator, place villages, humans and creatures on the map.
     *
     * @param simulationOptions Simulation options provided by the user
     */
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
            creatures.add(
                    Creature.createNew(Creature.CreatureType.HAMSTER, this));
        }
    }

    /**
     * Perform a single simulation tick for all villages and creatures. Check if
     * all villages belong to one team. If so, mark the simulation as complete.
     */
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

    /**
     * Get the world height map
     *
     * @return Height map
     */
    public HeightMap getHeightMap() {
        return heightMap;
    }

    /**
     * Get creature list
     *
     * @return List of creatures
     */
    public List<Creature> getCreatureList() {
        return creatures;
    }

    /**
     * Get village list
     *
     * @return List of villages
     */
    public List<Village> getVillageList() {
        return villages;
    }

    /**
     * Check if the simulation is complete
     *
     * @return Whether the simulation is complete
     */
    public boolean isSimulationComplete() {
        return simulationComplete;
    }

    /**
     * Kill the given creature. Remove it from the list and create a new one of
     * exactly the same type.
     *
     * @param creature Creature to be killed
     */
    public void killCreature(Creature creature) {
        creatures.remove(creature);
        creatures.add(creature.resurrect());
    }

    /**
     * Get the nearest enemy creature within the specified distance. Note that
     * all non-human creatures are treated as enemies in this context.
     *
     * @param requester Human whose enemies are to be taken into account
     * @param distance  Maximum distance between the given human and creature
     * @return The nearest enemy creature or null if there is none within
     * specified distance.
     */
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
            if (creatureDistance.getKey().getTeamID() == requester.getTeamID())
                continue;
            if (creatureDistance.getValue() > distance * distance)
                return null;
            return creatureDistance.getKey();
        }
        return null;
    }

    /**
     * Get a random item based on biome where the given human is
     *
     * @param requester Human whose position is to be taken into account
     * @return Resource item type or null if the human did not have enough luck
     */
    public Inventory.ItemType collectResource(Human requester) {
        int randomNumber = random.nextInt(1000);
        if (getBiomeAt(requester.getPosition()) ==
                BiomeConverter.Biome.PLAINS) {
            if (randomNumber > 990) {
                return Inventory.ItemType.WOOD;
            }
            else if (randomNumber < 4) {
                return Inventory.ItemType.STONE;
            }
            else if (5 < randomNumber && randomNumber < 12) {
                return Inventory.ItemType.WHEAT;
            }
            else
                return null;
        }
        else if (getBiomeAt(requester.getPosition()) ==
                BiomeConverter.Biome.MOUNTAINS) {
            if (randomNumber > 990) {
                return Inventory.ItemType.STONE;
            }
            else
                return null;
        }
        else {
            if (randomNumber == 69) {
                return Inventory.ItemType.SWORD;
            }
            else if (randomNumber == 420) {
                return Inventory.ItemType.ARMOR;
            }
            else
                return null;
        }
    }

    /**
     * Get the world map size
     *
     * @return Map size
     */
    public int getMapSize() {
        return simulationOptions.mapSize;
    }

    /**
     * Get amount of ticks passed since staring the simulation
     *
     * @return Tick counter value
     */
    public int getTickCounter() {
        return tickCounter;
    }

    /**
     * Get number of teams
     *
     * @return Number of teams
     */
    public int getTeamCount() {
        return simulationOptions.nTeams;
    }
}
