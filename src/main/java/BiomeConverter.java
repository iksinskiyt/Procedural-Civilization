import java.awt.*;
import java.util.EnumMap;

public class BiomeConverter {
    public enum Biome {
        OCEAN,
        PLAINS,
        MOUNTAINS
    }

    public static Biome getBiome(double height) {
        if (height < 1.0 / 3)
            return Biome.OCEAN;
        else if (height < 2.0 / 3)
            return Biome.PLAINS;
        else
            return Biome.MOUNTAINS;
    }

    public static int[] getBiomeColor(double height) {
        if (height < 1.0 / 3)
            return new int[]{(int) (height * 255), (int) (height * 255), 255};
        else if (height < 2.0 / 3)
            return new int[]{(int) ((height - 1.0 / 3) * 1.2 * 255),
                    (int) ((height / 2 + 2.0 / 3) * 255),
                    (int) ((height - 1.0 / 3) * 1.2 * 255)};
        else
            return new int[]{(int) ((height - 1.0 / 3) * 1.5 * 255),
                    (int) ((height - 1.0 / 3) * 1.5 * 255),
                    (int) ((height - 1.0 / 3) * 1.5 * 255)};
    }
}
