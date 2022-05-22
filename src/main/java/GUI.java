import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class GUI {
    class UserInputDialog extends JDialog {
        private final JTextField tfMapSize;
        private final JTextField tfNoiseScale;
        private final JTextField tfNoiseOctaves;

        public UserInputDialog() {
            super();
            tfMapSize = new JTextField("800");
            tfNoiseScale = new JTextField("1.0");
            tfNoiseOctaves = new JTextField("10");
            JButton bStart = new JButton("Start");
            bStart.addActionListener(actionEvent -> setVisible(false));
            add(new JLabel("Map size:"));
            add(tfMapSize);
            add(new JLabel("Noise scale:"));
            add(tfNoiseScale);
            add(new JLabel("Noise octaves:"));
            add(tfNoiseOctaves);
            add(new JLabel(""));
            add(bStart);
            setSize(400, 200);
            setResizable(false);
            setLayout(new GridLayout(4, 2));
            setModalityType(ModalityType.APPLICATION_MODAL);
            addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent windowEvent) {
                }

                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }

                @Override
                public void windowClosed(WindowEvent windowEvent) {
                }

                @Override
                public void windowIconified(WindowEvent windowEvent) {
                }

                @Override
                public void windowDeiconified(WindowEvent windowEvent) {
                }

                @Override
                public void windowActivated(WindowEvent windowEvent) {
                }

                @Override
                public void windowDeactivated(WindowEvent windowEvent) {
                }
            });
            setVisible(true);
        }

        public PerlinOptions getPerlinOptions() {
            PerlinOptions perlinOptions = new PerlinOptions();

            try {
                perlinOptions.size = Integer.parseInt(tfMapSize.getText());
                perlinOptions.noiseScale =
                        Double.parseDouble(tfNoiseScale.getText());
                perlinOptions.noiseOctaves =
                        Integer.parseInt(tfNoiseOctaves.getText());
            } catch (NumberFormatException e) {
                return null;
            }

            return perlinOptions;
        }
    }

    private Map map;
    private int[] seed;
    private PerlinOptions perlinOptions;
    private SimulationOptions simulationOptions;
    private int nVillages;

    public GUI() {
    }

    public void startSimulation() {
        UserInputDialog dialog = new UserInputDialog();
        perlinOptions = dialog.getPerlinOptions();
        if (perlinOptions == null) {
            // TODO: Show error dialog here
            System.exit(1);
        }
        System.out.println(perlinOptions.size);
        System.out.println(perlinOptions.noiseScale);
        System.out.println(perlinOptions.noiseOctaves);
        map = new Map(perlinOptions, simulationOptions);
    }

    public void getOptionsFromUser() {
    }

    public void showSimulation() {
        HeightMap heightMap = map.getHeightMap();
        List<Creature> creatures = map.getCreatureList();
        List<Village> villages = map.getVillageList();
        // TODO: Display simulation state using the above parameters
    }

    public void simulationTick() {
        map.simulationTick();
    }

    public boolean isSimulationComplete() {
        return map.isSimulationComplete();
    }
}
