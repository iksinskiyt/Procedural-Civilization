import java.security.SecureRandom;
import java.util.Random;

public class Perlin {
    double scale = 1;
    int size;
    int octaves;

    public Perlin (double[] perlinOptions){
        size = perlinOptions[0];
        scale = perlinOptions[1];
        octaves = perlinOptions[2]
    }

    Random rand = new Random();
    SecureRandom random = new SecureRandom();
    public double average = 0;

    double[] seed = new double[size*size];

    double[][] generatePerlinNoise2D(){
        double[][] interpolatedNoise = new double[size][size];
        for(int i =0; i < size*size ;i++) {
            seed[i] = rand.nextDouble();
        }
        for(int i = 0; i < size; i++){
            for(int k = 0; k < size; k++){
                double scaleAcc = 0;
                double noise = 0;
                for(int j = 0; j< octaves; j++){
                    int pitch = size >> j;

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
        for(int i = 0; i<size;i++){
            for(int j = 0; j<size;j++){
                if(maxValue<interpolatedNoise[i][j]){
                    maxValue = interpolatedNoise[i][j];
                }
                if(minValue>interpolatedNoise[i][j]){
                    minValue = interpolatedNoise[i][j];
                }
            }
        }  

        for(int i = 0; i<size;i++){
            for(int j = 0; j<size;j++){
                interpolatedNoise[i][j] = ((interpolatedNoise[i][j] - minValue)/(maxValue-minValue));
            }
        }

        double avgTemp = 0;
        
        for(int i = 0; i<size;i++){
            for(int j = 0; j<size;j++){
                 avgTemp += interpolatedNoise[i][j];
            }
        }

        average = avgTemp/(Main.noiseSize*Main.noiseSizeY);

       // System.out.println(average);

        return interpolatedNoise;
    }
}