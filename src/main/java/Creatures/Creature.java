package Creatures;

import Simulation.Village;
import Terrain.BiomeConverter;
import Simulation.Inventory;
import Terrain.Map;
import Structures.Position;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * An abstract class describing a creature
 */
public abstract class Creature {
    /**
     * Creature type
     */
    public enum CreatureType {
        /**
         * Cow - moves slowly, drops meat and leather. Spawns only in plains
         * biome
         */
        COW,

        /**
         * Hamster - moves quickly, drops meat. Spawns in plains and mountains
         * biomes
         */
        HAMSTER
    }

    /**
     * Inventory object
     */
    protected Inventory inventory;

    /**
     * Creature's current position on the map
     */
    protected Position position;

    /**
     * Health. A non-positive number means that the creature is dead.
     */
    protected int health;

    /**
     * Attack strength. Used only by Humans.
     */
    protected int attackStrength;

    /**
     * Movement speed
     */
    protected final int speed;

    /**
     * Map were the creature lives
     */
    protected Map parentMap;

    /**
     * Random number generator object. Used to determine movement pattern.
     */
    protected Random random;

    /**
     * Construct a new creature
     *
     * @param parentMap         A map were the creature is located
     * @param position          Starting position
     * @param health            Starting health
     * @param attackStrength    Attack strength
     * @param speed             Speed
     * @param inventoryCapacity Inventory capacity
     */
    Creature(Map parentMap, Position position, int health, int attackStrength,
             int speed, int inventoryCapacity) {
        this.parentMap = parentMap;
        this.position = position;
        inventory = new Inventory(inventoryCapacity);

        this.health = health;
        this.attackStrength = attackStrength;
        this.speed = speed;
        random = new Random();
    }

    /**
     * Create a new creature of a given type. In order to create a new Human use
     * {@link Human#Human(Village, Position)}
     *
     * @param creatureType Type of creature to be created
     * @param parentMap    A map where the creature lives
     * @return A new creature object
     */
    public static Creature createNew(CreatureType creatureType, Map parentMap) {
        switch (creatureType) {
            case COW -> {
                return new Cow(parentMap, parentMap.getRandomPosition(
                        List.of(BiomeConverter.Biome.PLAINS)));
            }
            case HAMSTER -> {
                return new Hamster(parentMap, parentMap.getRandomPosition(
                        List.of(BiomeConverter.Biome.PLAINS,
                                BiomeConverter.Biome.MOUNTAINS)));
            }
        }
        return null;
    }

    /**
     * Attack the creature and kill if necessary
     *
     * @param damage Damage to take
     * @param teamID Team ID of a Human who attacked
     */
    public void attack(int damage, int teamID) {
        health -= damage;
        if (health <= 0)
            parentMap.killCreature(this);
    }

    /**
     * Generate a new random position within the creature's speed limit
     *
     * @return A new position
     */
    protected Position getNewRandomPosition() {
        int currentSpeed = speed;
        switch (parentMap.getBiomeAt(position)) {
            case MOUNTAINS -> currentSpeed /= 2;
            case OCEAN -> currentSpeed /= 4;
        }
        return new Position(Math.min(Math.max(0, position.x +
                        random.nextInt(currentSpeed + 1) *
                                (random.nextBoolean() ? 1 : -1)),
                parentMap.getMapSize() - 1), Math.min(Math.max(0, position.y +
                        random.nextInt(currentSpeed + 1) *
                                (random.nextBoolean() ? 1 : -1)),
                parentMap.getMapSize() - 1));
    }

    /**
     * Make a random move after making sure that the new position meets the
     * biome limit
     */
    public void move() {
        Position newRandomPosition = getNewRandomPosition();
        if (parentMap.getBiomeAt(newRandomPosition) !=
                BiomeConverter.Biome.OCEAN ||
                parentMap.getBiomeAt(position) == BiomeConverter.Biome.OCEAN)
            position = newRandomPosition;
    }

    /**
     * Take the creature's inventory
     *
     * @return Inventory object
     */
    public Inventory takeInventory() {
        return inventory;
    }

    /**
     * Get creature's position
     *
     * @return Position object
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Check if the creature is alive
     *
     * @return Whether the creature is alive
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Create a new creature of the same type and parent map (but possibly a
     * different starting position)
     *
     * @return A new creature
     */
    public abstract Creature resurrect();

    /**
     * Get color of the icon representing the creature in the main window
     *
     * @return Color object
     */
    public abstract Color getIconColor();

    /**
     * Get team ID of the creature
     *
     * @return Always -1, overridden by Human class
     */
    public int getTeamID() {
        return -1;
    }
}
