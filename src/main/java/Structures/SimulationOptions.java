package Structures;

/**
 * Simulation options provided by the user
 */
public class SimulationOptions {
    // General simulation options

    /**
     * Map size
     */
    public int mapSize;

    /**
     * Number of teams
     */
    public int nTeams;

    /**
     * Team population
     */
    public int teamPopulation;

    /**
     * Number of cows
     */
    public int nCows;

    /**
     * Number of hamsters
     */
    public int nHamsters;

    /**
     * Simulation speed [ms/tick]
     */
    public int simulationSpeed;

    // Perlin noise options

    /**
     * Perlin noise scale
     */
    public double noiseScale;

    /**
     * Number of Perlin noise octaves
     */
    public int noiseOctaves;
}
