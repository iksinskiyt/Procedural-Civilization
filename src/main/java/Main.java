import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class Main {

    public static void main(String[] args) {
        Perlin perlin = new Perlin();
        double[] x = new double[100];
        for(int i = 0;i<100;i++){
            x[i] = (double)i/10;
        }
        double[] generatedNoise = perlin.generatePerlinNoise();
        for(int i = 0; i<5;i++){
            System.out.println(generatedNoise[i]);
        }
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", x,generatedNoise);
        new SwingWrapper(chart).displayChart();
    }

}
