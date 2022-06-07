package Terrain;

/**
 * Various biome-related methods
 */
public class BiomeConverter {
    /**
     * Biome type
     */
    public enum Biome {
        /**
         * Ocean biome (slowest movement, only Humans can enter)
         */
        OCEAN,

        /**
         * Plains biome (fast movement, Villages spawn there)
         */
        PLAINS,

        /**
         * Mountains biome (slow movement, only Hamsters spawn there)
         */
        MOUNTAINS
    }

    /**
     * Get biome by height
     *
     * @param height Height to get biome for
     * @return Biome for the given height
     */
    public static Biome getBiome(double height) {
        if (height < 1.0 / 3)
            return Biome.OCEAN;
        else if (height < 2.0 / 3)
            return Biome.PLAINS;
        else
            return Biome.MOUNTAINS;
    }

    /**
     * Get biome color for the given height
     *
     * @param height Height to generate color for
     * @return An array of 3 integers describing a color in RGB color space
     */
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
