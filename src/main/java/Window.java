import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    Perlin perlin = new Perlin();
    double[][] generated2DNoise = perlin.generatePerlinNoise2D();
    public Window(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setUndecorated(true);
        setSize(new Dimension(Main.noiseSize,Main.noiseSizeY));
        setVisible(true);

    }
    public void paint(Graphics g){
        for(int i =0;i<Main.noiseSize;i++){
            for(int j = 0; j<Main.noiseSizeY;j++){
                if(generated2DNoise[i][j] < perlin.average){
                    g.setColor(new Color(0,0,(int)(generated2DNoise[i][j]*255),255));
                } else if(generated2DNoise[i][j] >= perlin.average && generated2DNoise[i][j] < perlin.average * 1.10){
                    g.setColor(new Color(0,(int)(generated2DNoise[i][j]*255),0,255));
                } else {
                    g.setColor(new Color((int)(generated2DNoise[i][j]*255),(int)(generated2DNoise[i][j]*255),(int)(generated2DNoise[i][j]*255),255));
                }

                g.drawLine(i,j,i,j);

            }
        }
    }
}
