import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class GUI {
    class ErrorDialog extends JDialog {
        public ErrorDialog(String message)
        {
            super();
            setLayout(new BorderLayout());
            add(new JLabel("An error occurred: " + message, SwingConstants.CENTER), BorderLayout.CENTER);
            JButton okButton = new JButton("OK");
            add(okButton, BorderLayout.SOUTH);
            okButton.addActionListener(actionEvent -> setVisible(false));
            setSize(400, 100);
            setResizable(false);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setVisible(true);
        }
    }

    class UserInputDialog extends JDialog {
        private final JTextField tfMapSize;
        private final JTextField tfNoiseScale;
        private final JTextField tfNoiseOctaves;
        private final JTextField tfNTeams;
        private final JTextField tfTeamPopulation;
        private final JTextField tfNCows;
        private final JTextField tfNHamsters;

        public UserInputDialog() {
            super();
            tfMapSize = new JTextField("800");
            tfNoiseScale = new JTextField("1.0");
            tfNoiseOctaves = new JTextField("10");
            tfNTeams = new JTextField("4");
            tfTeamPopulation = new JTextField("4");
            tfNCows = new JTextField("4");
            tfNHamsters = new JTextField("4");
            JButton bStart = new JButton("Start");
            bStart.addActionListener(actionEvent -> setVisible(false));
            add(new JLabel("Map size:"));
            add(tfMapSize);
            add(new JLabel("Noise scale:"));
            add(tfNoiseScale);
            add(new JLabel("Noise octaves:"));
            add(tfNoiseOctaves);
            add(new JLabel("Number of teams:"));
            add(tfNTeams);
            add(new JLabel("Team population:"));
            add(tfTeamPopulation);
            add(new JLabel("Number of cows:"));
            add(tfNCows);
            add(new JLabel("Number of hamsters:"));
            add(tfNHamsters);
            add(new JLabel(""));
            add(bStart);
            setSize(400, 300);
            setResizable(false);
            setLayout(new GridLayout(8, 2));
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
                new ErrorDialog(e.getMessage());
                return null;
            }

            return perlinOptions;
        }

        public SimulationOptions getSimulationOptions()
        {
            SimulationOptions simulationOptions = new SimulationOptions();

            try {
                simulationOptions.nTeams = Integer.parseInt(tfNTeams.getText());
                simulationOptions.teamPopulation = Integer.parseInt(tfTeamPopulation.getText());
                simulationOptions.nCows = Integer.parseInt(tfNCows.getText());
                simulationOptions.nHamsters = Integer.parseInt(tfNHamsters.getText());
            }
            catch (NumberFormatException e)
            {
                new ErrorDialog(e.getMessage());
                return null;
            }

            return simulationOptions;
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
        map = new Map(perlinOptions, simulationOptions);
    }

    public void getOptionsFromUser() {
        do {
            UserInputDialog dialog = new UserInputDialog();
            perlinOptions = dialog.getPerlinOptions();
            simulationOptions = dialog.getSimulationOptions();
        } while(perlinOptions == null || simulationOptions == null);
        System.out.println(perlinOptions.size);
        System.out.println(perlinOptions.noiseScale);
        System.out.println(perlinOptions.noiseOctaves);
        System.out.println(simulationOptions.nTeams);
        System.out.println(simulationOptions.teamPopulation);
        System.out.println(simulationOptions.nCows);
        System.out.println(simulationOptions.nHamsters);
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
