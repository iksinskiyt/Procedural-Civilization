import java.util.Random;

public class Perlin {
    Random rand = new Random();
    double[] seed = new double[100];
    double[] interpolatedNoise = new double[100];
    double[] generatePerlinNoise(){
        for(int i =0; i < 100 ;i++) {
            seed[i] = rand.nextDouble();
        }
        for(int i = 0; i < 100; i++){
            double scale = 1.0;
            double scaleAcc = 0;
            for(int j = 0; j< 7; j++){
                int pitch = 100 >> j;
                int sample1 = (i / pitch) * pitch;
                int sample2 = (sample1 + pitch) % 100;
                double blend = (double)(i - sample1) / (double)pitch;
                double sample = (1.0 - blend) * seed[sample1] + blend * seed[sample2];
                scaleAcc+=scale;
                scale = scale/2;
                interpolatedNoise[i] += (sample*scale)/scaleAcc;
            }
        }
        return interpolatedNoise;
    }
}