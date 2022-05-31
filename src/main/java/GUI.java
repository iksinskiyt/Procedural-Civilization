import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUI {
    class ErrorDialog extends JDialog {
        public ErrorDialog(String message) {
            super();
            setLayout(new BorderLayout());
            add(new JLabel("An error occurred: " + message,
                    SwingConstants.CENTER), BorderLayout.CENTER);
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

        public SimulationOptions getSimulationOptions() {
            SimulationOptions simulationOptions = new SimulationOptions();

            try {
                simulationOptions.mapSize = Integer.parseInt(tfMapSize.getText());
                simulationOptions.nTeams = Integer.parseInt(tfNTeams.getText());
                simulationOptions.teamPopulation =
                        Integer.parseInt(tfTeamPopulation.getText());
                simulationOptions.nCows = Integer.parseInt(tfNCows.getText());
                simulationOptions.nHamsters =
                        Integer.parseInt(tfNHamsters.getText());
                simulationOptions.noiseScale = Double.parseDouble(tfNoiseScale.getText());
                simulationOptions.noiseOctaves = Integer.parseInt(tfNoiseOctaves.getText());
            } catch (NumberFormatException e) {
                new ErrorDialog(e.getMessage());
                return null;
            }

            return simulationOptions;
        }
    }

    private MainWindow mainWindow;

    public GUI() {
    }

    public void openMainWindow(Map map) {
        mainWindow = new MainWindow(map);
    }

    public SimulationOptions getOptionsFromUser() {
        SimulationOptions simulationOptions;
        do {
            UserInputDialog dialog = new UserInputDialog();
            simulationOptions = dialog.getSimulationOptions();
        } while (simulationOptions == null);
        System.out.println(simulationOptions.mapSize);
        System.out.println(simulationOptions.noiseScale);
        System.out.println(simulationOptions.noiseOctaves);
        System.out.println(simulationOptions.nTeams);
        System.out.println(simulationOptions.teamPopulation);
        System.out.println(simulationOptions.nCows);
        System.out.println(simulationOptions.nHamsters);
        return simulationOptions;
    }

    public void showSimulation() {
        mainWindow.repaint();
    }
}
