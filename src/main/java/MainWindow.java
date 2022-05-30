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

    public class MapPanel extends JPanel {
        private void drawCreatureIcon(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillOval(x - 3, y - 3, 7, 7);
            g.setColor(Color.BLACK);
            g.drawOval(x - 3, y - 3, 7, 7);
        }

        private void drawHumanIcon(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x - 3, y - 3, 7, 7);
            g.setColor(Color.BLACK);
            g.drawRect(x - 3, y - 3, 7, 7);
        }

        private void drawVillageIcon(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillPolygon(new int[]{x, x + 8, x, x - 8},
                    new int[]{y + 8, y, y - 8, y}, 4);
            g.setColor(Color.BLACK);
            g.drawPolygon(new int[]{x, x + 8, x, x - 8},
                    new int[]{y + 8, y, y - 8, y}, 4);
        }

        private Color getTeamColor(int teamID) {
            return new Color((teamID & 4) > 0 ? 255 : 0,
                    (teamID & 2) > 0 ? 255 : 0, (teamID & 1) > 0 ? 255 : 0);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(mapImage, 0, 0, null);
            for (Creature creature : creatures) {
                Color color = null;
                if (creature instanceof Cow)
                    color = new Color(0xffff00);
                else if (creature instanceof Hamster)
                    color = new Color(0x00ffff);
                Position position = creature.getPosition();
                drawCreatureIcon(g, position.x, position.y, color);
            }
            for (Village village : villages) {
                Position villagePosition = village.getPosition();
                drawVillageIcon(g, villagePosition.x, villagePosition.y, getTeamColor(village.getTeamID()));
                for (Human human : village.getVillagers()) {
                    Position position = human.getPosition();
                    drawHumanIcon(g, position.x, position.y,
                            getTeamColor(human.getTeamID()));
                }
            }
        }
    }

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

        MapPanel mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(windowSize, windowSize));
        add(mapPanel);
        pack();
        setTitle("Procedural Civilization");
        setResizable(false);
        setVisible(true);
    }
}
