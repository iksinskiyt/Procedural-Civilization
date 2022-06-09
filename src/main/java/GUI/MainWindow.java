package GUI;

import Creatures.Creature;
import Creatures.Human;
import Terrain.BiomeConverter;
import Terrain.Map;
import Simulation.Village;
import Structures.HeightMap;
import Structures.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;

/**
 * Main window used to display the world and creatures
 */
class MainWindow extends JFrame {
    /**
     * List of creatures
     */
    private final List<Creature> creatures;

    /**
     * List of villages
     */
    private final List<Village> villages;

    /**
     * Image containing biome and height-colored terrain map
     */
    private final BufferedImage mapImage;

    /**
     * Panel where the world is supposed to be displayed
     */
    public class MapPanel extends JPanel {
        /**
         * Draw a circular icon representing a non-human creature
         *
         * @param g        Graphics object to draw on
         * @param position Creature position
         * @param color    Icon color
         */
        private void drawCreatureIcon(Graphics g, Position position,
                                      Color color) {
            g.setColor(color);
            g.fillOval(position.x - 3, position.y - 3, 7, 7);
            g.setColor(Color.BLACK);
            g.drawOval(position.x - 3, position.y - 3, 7, 7);
        }

        /**
         * Draw a square icon representing a human creature
         *
         * @param g        Graphics object to draw on
         * @param position Human position
         * @param color    Icon color
         * @param human    Human do draw icon for
         */
        private void drawHumanIcon(Graphics g, Position position, Color color,
                                   Human human) {
            g.setColor(color);
            g.fillRect(position.x - 3, position.y - 3, 7, 7);
            g.setColor(Color.BLACK);
            g.drawRect(position.x - 3, position.y - 3, 7, 7);
            drawStatIcon(g, position, human);
        }

        /**
         * Draw a large square icon representing a village
         *
         * @param g        Graphics object to draw on
         * @param position Village position
         * @param color    Icon color
         */
        private void drawVillageIcon(Graphics g, Position position,
                                     Color color) {
            g.setColor(color);
            g.fillPolygon(new int[]{position.x, position.x + 8, position.x,
                            position.x - 8},
                    new int[]{position.y + 8, position.y, position.y - 8,
                            position.y}, 4);
            g.setColor(Color.BLACK);
            g.drawPolygon(new int[]{position.x, position.x + 8, position.x,
                            position.x - 8},
                    new int[]{position.y + 8, position.y, position.y - 8,
                            position.y}, 4);
        }

        /**
         * Draw a statistics icon for a given human
         *
         * @param g        Graphics object to draw on
         * @param position Human position
         * @param human    Human object
         */
        private void drawStatIcon(Graphics g, Position position, Human human) {
            int pixelHealthWidth;
            int pixelArmorWidth;
            int pixelSwordWidth;
            int health = human.getHealth();
            int maxHealth = human.getMaxHealth();
            int armor = human.getArmor();
            int maxArmor = human.getMaxArmor();
            int sword = human.getSword();
            int maxSword = human.getMaxSword();

            pixelHealthWidth =
                    (int) Math.ceil((double) health * 5 / (double) maxHealth);
            pixelArmorWidth =
                    (int) Math.ceil((double) armor * 5 / (double) maxArmor);
            pixelSwordWidth =
                    (int) Math.ceil((double) sword * 5 / (double) maxSword);

            g.setColor(new Color(237, 31, 36));
            g.fillRect(position.x - 2, position.y - 2, 6, 3);

            g.setColor(new Color(65, 182, 73));
            g.drawRect(position.x - 2, position.y - 2, pixelHealthWidth, 0);

            g.setColor(new Color(137, 137, 137));
            g.drawRect(position.x - 2, position.y - 1, pixelArmorWidth, 0);

            g.setColor(new Color(249, 155, 77));
            g.drawRect(position.x - 2, position.y, pixelSwordWidth, 0);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(mapImage, 0, 0, null);
            for (Creature creature : creatures) {
                Position position = creature.getPosition();
                drawCreatureIcon(g, position, creature.getIconColor());
            }
            for (Village village : villages) {
                Position villagePosition = village.getPosition();
                drawVillageIcon(g, villagePosition, village.getTeamColor());
                for (Human human : village.getVillagers()) {
                    Position position = human.getPosition();
                    drawHumanIcon(g, position, village.getTeamColor(), human);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
    }

    /**
     * Construct a new main window
     *
     * @param map Map to render the map image from
     * @param gui GUI object to use
     */
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
        mapPanel.setPreferredSize(
                new Dimension(map.getMapSize(), map.getMapSize()));
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
