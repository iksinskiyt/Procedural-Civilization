import java.util.Random;

public class Perlin {
    Random rand = new Random();
    double[] seed = new double[Main.noiseSize*Main.noiseSizeY];
    double[][] seed2D = new double[Main.noiseSize][Main.noiseSizeY];

    double[] generatePerlinNoise(){
        double[] interpolatedNoise = new double[Main.noiseSize];
        for(int i =0; i < Main.noiseSize ;i++) {
            seed[i] = rand.nextDouble();
        }
        for(int i = 0;i<Main.noiseSize;i++){
            for(int j = 0; j<Main.noiseSizeY;j++){
                seed2D[i][j] = rand.nextDouble();
            }
        }
        for(int i = 0; i < Main.noiseSize; i++){
            double scale = 1.0;
            double scaleAcc = 0;
            for(int j = 0; j< Main.octaves; j++){
                int pitch = Main.noiseSize >> j;
                int sample1 = (i / pitch) * pitch;
                int sample2 = (sample1 + pitch) % Main.noiseSize;
                double blend = (double)(i - sample1) / (double)pitch;
                double sample = (1.0 - blend) * seed[sample1] + blend * seed[sample2];
                scaleAcc+=scale;
                scale = scale/2;
                interpolatedNoise[i] += (sample*scale)/scaleAcc;
            }
        }
        return interpolatedNoise;
    }
    double[][] generatePerlinNoise2D(){
        double[][] interpolatedNoise = new double[Main.noiseSize][Main.noiseSizeY];
        for(int i =0; i < Main.noiseSize*Main.noiseSizeY ;i++) {
            seed[i] = rand.nextDouble();
        }
        for(int i = 0;i<Main.noiseSize;i++){
            for(int j = 0; j<Main.noiseSizeY;j++){
                seed2D[i][j] = rand.nextDouble();
            }
        }
        for(int i = 0; i < Main.noiseSize; i++){
            for(int k = 0; k < Main.noiseSizeY; k++){
                double scale = 1;
                double scaleAcc = 0;
                double noise = 0;

                for(int j = 0; j< Main.octaves; j++){
                    int pitch = Main.noiseSize >> j;

                    int sampleX1 = (i / pitch) * pitch;
                    int sampleY1 = (k / pitch) * pitch;

                    int sampleX2 = (sampleX1 + pitch) % Main.noiseSize;
                    int sampleY2 = (sampleY1 + pitch) % Main.noiseSize;

                    double blendX = (double)(i - sampleX1) / (double)pitch;
                    double blendY = (double)(k - sampleY1) / (double)pitch;

                    double sampleX = (1.0 - blendX) * seed[sampleY1 * Main.noiseSize + sampleX1] + blendX * seed[sampleY1 * Main.noiseSize + sampleX2];
                    double sampleY = (1.0 - blendX) * seed[sampleY2 * Main.noiseSize + sampleX1] + blendX * seed[sampleY2 * Main.noiseSize + sampleX2];

                    scaleAcc+=scale;
                    noise += (((blendY * (sampleY - sampleX) + sampleX)*scale));
                    scale = scale/2;
                }
                interpolatedNoise[i][k] = noise/scaleAcc;
            }
        }
        return interpolatedNoise;
    }
}