import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    private class MessageDialog extends JDialog {
        public MessageDialog(String message) {
            super();
            setLayout(new BorderLayout());
            add(new JLabel(message, SwingConstants.CENTER),
                    BorderLayout.CENTER);
            JButton okButton = new JButton("OK");
            add(okButton, BorderLayout.SOUTH);
            okButton.addActionListener(actionEvent -> setVisible(false));
            pack();
            setResizable(false);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private class YesNoDialog extends JDialog {
        boolean yes = false;

        private class YesNoPanel extends JPanel {
            public YesNoPanel()
            {
                JButton yesButton = new JButton("Yes");
                JButton noButton = new JButton("No");
                add(yesButton);
                add(noButton);
                yesButton.addActionListener(actionEvent -> {
                    yes = true;
                    YesNoDialog.this.setVisible(false);
                });
                noButton.addActionListener(actionEvent -> YesNoDialog.this.setVisible(false));
            }
        }

        public YesNoDialog(String message)
        {
            super();
            setLayout(new BorderLayout());
            add(new JLabel(message, SwingConstants.CENTER),
                    BorderLayout.CENTER);
            add(new YesNoPanel(), BorderLayout.SOUTH);
            pack();
            setResizable(false);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public boolean getYes()
        {
            return yes;
        }
    }

    private class UserInputDialog extends JDialog {
        private final JTextField tfMapSize;
        private final JTextField tfNoiseScale;
        private final JTextField tfNoiseOctaves;
        private final JTextField tfNTeams;
        private final JTextField tfTeamPopulation;
        private final JTextField tfNCows;
        private final JTextField tfNHamsters;
        private final JTextField tfSimulationSpeed;

        private JTextField addNewInput(String label, String defaultValue)
        {
            add(new JLabel(label));
            JTextField textField = new JTextField(defaultValue);
            add(textField);
            return textField;
        }

        public UserInputDialog() {
            super();
            tfMapSize = addNewInput("Map size", "800");
            tfNoiseScale = addNewInput("Noise scale", "1.0");
            tfNoiseOctaves = addNewInput("Noise octaves", "10");
            tfNTeams = addNewInput("Number of teams", "4");
            tfTeamPopulation = addNewInput("Team population", "8");
            tfNCows = addNewInput("Number of cows", "10");
            tfNHamsters = addNewInput("Number of hamsters", "5");
            tfSimulationSpeed = addNewInput("Simulation speed", "32");
            JButton bStart = new JButton("Start");
            bStart.addActionListener(actionEvent -> setVisible(false));
            add(new Label(""));
            add(bStart);
            setResizable(false);
            setLayout(new GridLayout(9, 2, 20, 0));
            pack();
            setModalityType(ModalityType.APPLICATION_MODAL);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.exit(0);
                }
            });
            setTitle("Procedural Civilization (user input)");
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public SimulationOptions getSimulationOptions() {
            SimulationOptions simulationOptions = new SimulationOptions();

            try {
                simulationOptions.mapSize =
                        Integer.parseInt(tfMapSize.getText());
                if(simulationOptions.mapSize < 1)
                    throw new Exception("Map size must be a positive integer");
                if(simulationOptions.mapSize<512)
                    throw new Exception("Map size must be at least 512");
                simulationOptions.nTeams = Integer.parseInt(tfNTeams.getText());
                if(simulationOptions.nTeams < 1 || simulationOptions.nTeams > 8)
                    throw new Exception("Number of teams must be in range 1-8 (inclusive)");
                simulationOptions.teamPopulation =
                        Integer.parseInt(tfTeamPopulation.getText());
                if(simulationOptions.teamPopulation < 0)
                    throw new Exception("Team population must be a natural number");
                simulationOptions.nCows = Integer.parseInt(tfNCows.getText());
                if(simulationOptions.nCows < 0)
                    throw new Exception("Number of cows must be a natural number");
                simulationOptions.nHamsters =
                        Integer.parseInt(tfNHamsters.getText());
                if(simulationOptions.nHamsters < 0)
                    throw new Exception("Number of hamsters must be a natural number");
                simulationOptions.noiseScale =
                        Double.parseDouble(tfNoiseScale.getText());
                simulationOptions.noiseOctaves =
                        Integer.parseInt(tfNoiseOctaves.getText());
                if(simulationOptions.noiseOctaves < 2 || simulationOptions.noiseOctaves > 10)
                    throw new Exception("Number of octaves must be in range 2-10 (inclusive)");
                simulationOptions.simulationSpeed = Integer.parseInt(tfSimulationSpeed.getText());
                if(simulationOptions.simulationSpeed < 1)
                    throw new Exception("Simulation speed must be a positive integer");
            } catch (NumberFormatException e) {
                showMessage("Invalid number format: " + e.getMessage(), false);
                return null;
            }
            catch (Exception e)
            {
                showMessage(e.getMessage(), false);
                return null;
            }

            return simulationOptions;
        }
    }

    private MainWindow mainWindow;

    public GUI() {
    }

    public void openMainWindow(Map map) {
        mainWindow = new MainWindow(map, this);
    }

    public void closeMainWindow() {
        mainWindow.dispose();
    }

    public SimulationOptions getOptionsFromUser() {
        SimulationOptions simulationOptions;
        do {
            UserInputDialog dialog = new UserInputDialog();
            simulationOptions = dialog.getSimulationOptions();
        } while (simulationOptions == null);
        return simulationOptions;
    }

    public void showSimulation() {
        mainWindow.repaint();
    }

    public void showMessage(String message, boolean fatal) {
        new MessageDialog(message);
        if(fatal)
            System.exit(1);
    }

    private boolean exitRequested = false;

    public void maybeExit()
    {
        YesNoDialog yesNoDialog = new YesNoDialog("Do you want to exit the simulation?");
        exitRequested = yesNoDialog.getYes();
    }

    public boolean getExitRequested()
    {
        return exitRequested;
    }
}
