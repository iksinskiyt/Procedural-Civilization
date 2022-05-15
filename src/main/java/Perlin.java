import java.security.SecureRandom;
import java.util.Random;

public class Perlin {
    Random rand = new Random();
    SecureRandom random = new SecureRandom();
    public double average = 0;

    double[] seed = new double[Main.noiseSize*Main.noiseSizeY];

    double[][] generatePerlinNoise2D(){
        double[][] interpolatedNoise = new double[Main.noiseSize][Main.noiseSizeY];
        for(int i =0; i < Main.noiseSize*Main.noiseSizeY ;i++) {
            seed[i] = rand.nextDouble();
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

        double maxValue = 0; 
        double minValue = 0;
        for(int i = 0; i<Main.noiseSize;i++){
            for(int j = 0; j<Main.noiseSizeY;j++){
                if(maxValue<interpolatedNoise[i][j]){
                    maxValue = interpolatedNoise[i][j];
                }
                if(minValue>interpolatedNoise[i][j]){
                    minValue = interpolatedNoise[i][j];
                }
            }
        }  

        for(int i = 0; i<Main.noiseSize;i++){
            for(int j = 0; j<Main.noiseSizeY;j++){
                interpolatedNoise[i][j] = ((interpolatedNoise[i][j] - minValue)/(maxValue-minValue));
            }
        }

        double avgTemp = 0;
        
        for(int i = 0; i<Main.noiseSize;i++){
            for(int j = 0; j<Main.noiseSizeY;j++){
                 avgTemp += interpolatedNoise[i][j];
            }
        }

        average = avgTemp/(Main.noiseSize*Main.noiseSizeY);

        System.out.println(average);

        return interpolatedNoise;
    }
}