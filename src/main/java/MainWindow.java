import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;

public class MainWindow extends JFrame {
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

        private void drawHumanIcon(Graphics g, int x, int y, Color color, Human human) {
            g.setColor(color);
            g.fillRect(x - 3, y - 3, 7, 7);
            g.setColor(Color.BLACK);
            g.drawRect(x - 3, y - 3, 7, 7);
            drawStatIcon(g, x, y, human);
        }

        private void drawVillageIcon(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillPolygon(new int[]{x, x + 8, x, x - 8},
                    new int[]{y + 8, y, y - 8, y}, 4);
            g.setColor(Color.BLACK);
            g.drawPolygon(new int[]{x, x + 8, x, x - 8},
                    new int[]{y + 8, y, y - 8, y}, 4);
        }

        private void drawStatIcon(Graphics g, int x, int y, Human human){
            int pixelHealthWidth;
            int pixelArmorWidth;
            int pixelSwordWidth;
            int health = human.getHealth();
            int maxHealth = human.getMaxHealth();
            int armor = human.getArmor();
            int maxArmor = human.getMaxArmor();
            int sword = human.getSword();
            int maxSword = human.getMaxSword();

            pixelHealthWidth = (int)Math.ceil((double)health*5/(double)maxHealth);
            pixelArmorWidth = (int)Math.ceil((double)armor*5/(double)maxArmor);
            pixelSwordWidth = (int)Math.ceil((double)sword*5/(double)maxSword);

            g.setColor(new Color(237, 31, 36));
            g.fillRect(x-2, y-2, 6, 3);

            g.setColor(new Color(65, 182, 73));
            g.drawRect(x-2, y-2, pixelHealthWidth, 0);

            g.setColor(new Color(137, 137, 137));
            g.drawRect(x-2, y-1, pixelArmorWidth, 0);
            
            g.setColor(new Color(249, 155, 77));
            g.drawRect(x-2, y, pixelSwordWidth, 0);
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
                            getTeamColor(human.getTeamID()),human);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
    }

    public MainWindow(Map map, GUI gui) {
        // Get all necessary references
        HeightMap heightMap = map.getHeightMap();
        this.creatures = map.getCreatureList();
        this.villages = map.getVillageList();

        // Draw a map image
        mapImage = new BufferedImage(map.getMapSize(), map.getMapSize(),
                BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster mapImageRaster = mapImage.getRaster();
        for (int i = 0; i < map.getMapSize(); i++) {
            for (int j = 0; j < map.getMapSize(); j++) {
                mapImageRaster.setPixel(i, j,
                        BiomeConverter.getBiomeColor(heightMap.height[i][j]));
            }
        }

        MapPanel mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(map.getMapSize(), map.getMapSize()));
        add(mapPanel);
        pack();
        setTitle("Procedural Civilization");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                gui.maybeExit();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
