import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MainWindow extends JFrame {
    final int windowSize;
    final HeightMap heightMap;
    final BufferedImage bufferedImage;
    final WritableRaster writableRaster;

    public MainWindow(int windowSize, HeightMap heightMap) {
        this.windowSize = windowSize;
        this.heightMap = heightMap;
        bufferedImage = new BufferedImage(windowSize, windowSize,
                BufferedImage.TYPE_3BYTE_BGR);
        writableRaster = bufferedImage.getRaster();
        setTitle("Procedural Civilization");
        setSize(windowSize, windowSize);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < windowSize; i++) {
            for (int j = 0; j < windowSize; j++) {
                writableRaster.setPixel(i, j,
                        BiomeConverter.getBiomeColor(heightMap.height[i][j]));
            }
        }
        g.drawImage(bufferedImage, 0, 0, null);
    }
}
