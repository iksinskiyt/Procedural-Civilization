package Terrain;

import Structures.SimulationOptions;

import java.util.Random;

class Perlin {
    double scale_base;
    int size;
    int octaves;

    public Perlin(SimulationOptions simulationOptions) {
        size = simulationOptions.mapSize;
        scale_base = simulationOptions.noiseScale;
        octaves = simulationOptions.noiseOctaves;
        seed = new double[size * size];
    }

    Random rand = new Random();
    public double average = 0;

    double[] seed;

    double[][] generatePerlinNoise2D() {
        double[][] interpolatedNoise = new double[size][size];
        for (int i = 0; i < size * size; i++) {
            seed[i] = rand.nextDouble();
        }
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                double scale = scale_base;
                double scaleAcc = 0;
                double noise = 0;
                for (int j = 0; j < octaves; j++) {
                    int pitch = size >> j;

                    int sampleX1 = (i / pitch) * pitch;
                    int sampleY1 = (k / pitch) * pitch;

                    int sampleX2 = (sampleX1 + pitch) % size;
                    int sampleY2 = (sampleY1 + pitch) % size;

                    double blendX = (double) (i - sampleX1) / (double) pitch;
                    double blendY = (double) (k - sampleY1) / (double) pitch;

                    double sampleX =
                            (1.0 - blendX) * seed[sampleY1 * size + sampleX1] +
                                    blendX * seed[sampleY1 * size + sampleX2];
                    double sampleY =
                            (1.0 - blendX) * seed[sampleY2 * size + sampleX1] +
                                    blendX * seed[sampleY2 * size + sampleX2];

                    scaleAcc += scale;
                    noise += (((blendY * (sampleY - sampleX) + sampleX) *
                            scale));
                    scale = scale / 2;
                }
                interpolatedNoise[i][k] = noise / scaleAcc;
            }
        }

        double maxValue = 0;
        double minValue = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (maxValue < interpolatedNoise[i][j]) {
                    maxValue = interpolatedNoise[i][j];
                }
                if (minValue > interpolatedNoise[i][j]) {
                    minValue = interpolatedNoise[i][j];
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                interpolatedNoise[i][j] =
                        ((interpolatedNoise[i][j] - minValue) /
                                (maxValue - minValue));
            }
        }

        double avgTemp = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                avgTemp += interpolatedNoise[i][j];
            }
        }

        average = avgTemp / (size * size);

        // System.out.println(average);

        return interpolatedNoise;
    }
}