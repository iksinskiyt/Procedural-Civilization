import java.awt.*;
import java.util.EnumMap;

public class BiomeConverter {
    public enum Biome {
        OCEAN,
        PLAINS,
        MOUNTAINS
    }

    public static EnumMap<Biome, int[]> BiomeColors =
            new EnumMap<>(Biome.class);

    static {
        BiomeColors.put(Biome.OCEAN, new int[]{0x00, 0x00, 0xff});
        BiomeColors.put(Biome.PLAINS, new int[]{0x00, 0xff, 0x00});
        BiomeColors.put(Biome.MOUNTAINS, new int[]{0x80, 0x80, 0x80});
    }

    public static Biome getBiome(double height) {
        if (height < 1.0 / 3)
            return Biome.OCEAN;
        else if (height < 2.0 / 3)
            return Biome.PLAINS;
        else
            return Biome.MOUNTAINS;
    }
}
