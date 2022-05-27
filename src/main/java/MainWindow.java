import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;

public class MainWindow extends JFrame {
    private final int windowSize;
    private final Map map;
    private final HeightMap heightMap;
    private final List<Creature> creatures;
    private final List<Village> villages;
    private final BufferedImage mapImage;

    public MainWindow(int windowSize, Map map) {
        this.windowSize = windowSize;
        this.map = map;

        // Get all necessary references
        this.heightMap = map.getHeightMap();
        this.creatures = map.getCreatureList();
        this.villages = map.getVillageList();

        // Draw a map image
        mapImage = new BufferedImage(windowSize, windowSize,
                BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster mapImageRaster = mapImage.getRaster();
        for (int i = 0; i < windowSize; i++) {
            for (int j = 0; j < windowSize; j++) {
                mapImageRaster.setPixel(i, j,
                        BiomeConverter.getBiomeColor(heightMap.height[i][j]));
            }
        }

        setTitle("Procedural Civilization");
        setSize(windowSize, windowSize);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(mapImage, 0, 0, null);
    }
}
