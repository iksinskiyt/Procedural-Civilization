package Creatures;

import Terrain.BiomeConverter;
import Simulation.Inventory;
import Terrain.Map;
import Structures.Position;

import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Creature {
    public enum CreatureType {
        COW, HAMSTER
    }

    protected Inventory inventory;
    protected Position position;
    protected int health;
    protected int attackStrength;
    protected final int speed;
    protected Map parentMap;
    protected Random random;

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

    public void attack(int damage, int teamID) {
        health -= damage;
        if (health <= 0)
            parentMap.killCreature(this);
    }

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

    public void move() {
        Position newRandomPosition = getNewRandomPosition();
        if (parentMap.getBiomeAt(newRandomPosition) !=
                BiomeConverter.Biome.OCEAN ||
                parentMap.getBiomeAt(position) == BiomeConverter.Biome.OCEAN)
            position = newRandomPosition;
    }

    public Inventory takeInventory() {
        return inventory;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract Creature resurrect();

    public abstract Color getIconColor();

    public int getTeamID() {
        return -1;
    }
}
