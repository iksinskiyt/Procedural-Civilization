import java.awt.*;
import java.util.EnumMap;

public class BiomeConverter {
    public enum Biome {
        OCEAN,
        PLAINS,
        MOUNTAINS
    }

    public static EnumMap<Biome, Color> BiomeColors = new EnumMap<Biome, Color>(Biome.class);
    static {
        BiomeColors.put(Biome.OCEAN, Color.BLUE);
        BiomeColors.put(Biome.PLAINS, Color.GREEN);
        BiomeColors.put(Biome.MOUNTAINS, Color.GRAY);
    }

    public static Biome getBiome(double height)
    {
        if(height < 1.0/3)
            return Biome.OCEAN;
        else if(height < 2.0/3)
            return Biome.PLAINS;
        else
            return Biome.MOUNTAINS;
    }
}
