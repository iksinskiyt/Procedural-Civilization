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

    public BiomeConverter.Biome getBiomeAt(Position position)
    {
        return BiomeConverter.getBiome(heightMap.height[position.x][position.y]);
    }

    private Position getRandomPosition(List<BiomeConverter.Biome> allowedBiomes) {
        Position position;
        while(true) {
            position = new Position(random.nextInt(simulationOptions.mapSize),
                    random.nextInt(simulationOptions.mapSize));
            BiomeConverter.Biome posBiome = getBiomeAt(position);
            for(BiomeConverter.Biome allowedBiome : allowedBiomes)
            {
                if(posBiome == allowedBiome)
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
            Village village =
                    new Village(getRandomPosition(
                            List.of(BiomeConverter.Biome.PLAINS)), i, this);
            villages.add(village);

            for (int j = 0; j < simulationOptions.teamPopulation; j++) {
                village.addVillager(new Human(i, this, village,
                        getRandomPosition(
                                List.of(BiomeConverter.Biome.PLAINS))));
            }
        }

        this.creatures = new ArrayList<>();

        for (int i = 0; i < simulationOptions.nCows; i++) {
            addCow();
        }

        for (int i = 0; i < simulationOptions.nHamsters; i++) {
            addHamster();
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
        createCreature(creature);
    }

    Creature getNearestAttackableCreature(Human requester) {
        // TODO: implementation
        return null;
    }

    Item collectResource(Human requester) {
        // TODO: implementation
        return null;
    }

    public int getMapSize()
    {
        return simulationOptions.mapSize;
    }

    public void createCreature(Creature creature){
        if(creature instanceof Cow){
            addCow();
        }
        if(creature instanceof Hamster){
            addHamster();
        }
    }

    public void addCow(){
        creatures.add(new Cow(this, getRandomPosition(List.of(BiomeConverter.Biome.PLAINS))));
    }

    public void addHamster(){
        creatures.add(new Hamster(this, getRandomPosition(List.of(BiomeConverter.Biome.MOUNTAINS))));
    }
}
