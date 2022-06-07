package GUI;

import Terrain.Map;
import Structures.SimulationOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User interaction
 */
public class GUI {
    /**
     * Modal message dialog with an arbitrary text and "OK" button
     */
    private static class MessageDialog extends JDialog {
        /**
         * Construct a new message dialog
         *
         * @param message Message text
         */
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

    /**
     * Modal dialog with an arbitrary text and "Yes" and "No" buttons
     */
    private static class YesNoDialog extends JDialog {
        /**
         * Whether the "Yes" button has been clicked
         */
        boolean yes = false;

        /**
         * Panel containing "Yes" and "No" buttons
         */
        private class YesNoPanel extends JPanel {
            /**
             * Construct a new Yes/No button panel
             */
            public YesNoPanel() {
                JButton yesButton = new JButton("Yes");
                JButton noButton = new JButton("No");
                add(yesButton);
                add(noButton);
                yesButton.addActionListener(actionEvent -> {
                    yes = true;
                    YesNoDialog.this.setVisible(false);
                });
                noButton.addActionListener(
                        actionEvent -> YesNoDialog.this.setVisible(false));
            }
        }

        /**
         * Construct a new Yes/No dialog
         *
         * @param message Message text
         */
        public YesNoDialog(String message) {
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

        /**
         * Get the user's answer
         *
         * @return Whether the "Yes" button has been clicked
         */
        public boolean getYes() {
            return yes;
        }
    }

    /**
     * Modal dialog used as a user input for simulation options
     */
    private class UserInputDialog extends JDialog {
        /**
         * Text field for map size
         */
        private final JTextField tfMapSize;

        /**
         * Text field for Perlin noise scale
         */
        private final JTextField tfNoiseScale;

        /**
         * Text field for number of Perlin noise octaves
         */
        private final JTextField tfNoiseOctaves;

        /**
         * Text field for number of teams
         */
        private final JTextField tfNTeams;

        /**
         * Text field for team population
         */
        private final JTextField tfTeamPopulation;

        /**
         * Text field for number of cows
         */
        private final JTextField tfNCows;

        /**
         * Text field for number of hamsters
         */
        private final JTextField tfNHamsters;

        /**
         * Text field for simulation speed
         */
        private final JTextField tfSimulationSpeed;

        /**
         * Add a new labeled text input field (a pair of a label and a button)
         *
         * @param label        Label text
         * @param defaultValue Default text input field value
         * @return A created text field object
         */
        private JTextField addNewInput(String label, String defaultValue) {
            add(new JLabel(label));
            JTextField textField = new JTextField(defaultValue);
            add(textField);
            return textField;
        }

        /**
         * Construct a new user input dialog
         */
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

        /**
         * Parse values given by the user and display non-fatal error messages
         * if necessary
         *
         * @return A SimulationOptions object if the parsing process has been
         * successful, null otherwise
         */
        public SimulationOptions getSimulationOptions() {
            SimulationOptions simulationOptions = new SimulationOptions();

            try {
                simulationOptions.mapSize =
                        Integer.parseInt(tfMapSize.getText());
                if (simulationOptions.mapSize < 1)
                    throw new Exception("Map size must be a positive integer");
                simulationOptions.nTeams = Integer.parseInt(tfNTeams.getText());
                if (simulationOptions.nTeams < 1 ||
                        simulationOptions.nTeams > 8)
                    throw new Exception(
                            "Number of teams must be in range 1-8 (inclusive)");
                simulationOptions.teamPopulation =
                        Integer.parseInt(tfTeamPopulation.getText());
                if (simulationOptions.teamPopulation < 0)
                    throw new Exception(
                            "Team population must be a natural number");
                simulationOptions.nCows = Integer.parseInt(tfNCows.getText());
                if (simulationOptions.nCows < 0)
                    throw new Exception(
                            "Number of cows must be a natural number");
                simulationOptions.nHamsters =
                        Integer.parseInt(tfNHamsters.getText());
                if (simulationOptions.nHamsters < 0)
                    throw new Exception(
                            "Number of hamsters must be a natural number");
                simulationOptions.noiseScale =
                        Double.parseDouble(tfNoiseScale.getText());
                simulationOptions.noiseOctaves =
                        Integer.parseInt(tfNoiseOctaves.getText());
                if (simulationOptions.noiseOctaves < 2)
                    throw new Exception(
                            "Number of octaves must be grater than 1");
                if (1 << (simulationOptions.noiseOctaves - 1) >
                        simulationOptions.mapSize)
                    throw new Exception(
                            "Number of octaves must not exceed log2(mapSize)+1");
                simulationOptions.simulationSpeed =
                        Integer.parseInt(tfSimulationSpeed.getText());
                if (simulationOptions.simulationSpeed < 1)
                    throw new Exception(
                            "Simulation speed must be a positive integer");
            }
            catch (NumberFormatException e) {
                showMessage("Invalid number format: " + e.getMessage(), false);
                return null;
            }
            catch (Exception e) {
                showMessage(e.getMessage(), false);
                return null;
            }

            return simulationOptions;
        }
    }

    /**
     * Main window object
     */
    private MainWindow mainWindow;

    /**
     * Open the main simulation window
     *
     * @param map An initialized simulation map
     */
    public void openMainWindow(Map map) {
        mainWindow = new MainWindow(map, this);
    }

    /**
     * Close the main simulation window
     */
    public void closeMainWindow() {
        mainWindow.dispose();
    }

    /**
     * Display the user input dialog and get the simulation options. Repeat
     * unless all options are retrieved successfully.
     *
     * @return A SimulationOptions structure containing the user-provided set of
     * options.
     */
    public SimulationOptions getOptionsFromUser() {
        SimulationOptions simulationOptions;
        do {
            UserInputDialog dialog = new UserInputDialog();
            simulationOptions = dialog.getSimulationOptions();
        }
        while (simulationOptions == null);
        return simulationOptions;
    }

    /**
     * Repaint the main window to show the simulation state after processing a
     * tick
     */
    public void showSimulation() {
        mainWindow.repaint();
    }

    /**
     * Show an arbitrary message
     *
     * @param message Message text
     * @param fatal   Whether a fatal error is reported and the program should
     *                exit after closing the message dialog.
     */
    public void showMessage(String message, boolean fatal) {
        new MessageDialog(message);
        if (fatal)
            System.exit(1);
    }

    /**
     * Whether simulation exit has been requested by the user
     */
    private boolean exitRequested = false;

    /**
     * Ask the user whether they want to exit the simulation and save the
     * answer
     */
    public void maybeExit() {
        YesNoDialog yesNoDialog =
                new YesNoDialog("Do you want to exit the simulation?");
        exitRequested = yesNoDialog.getYes();
    }

    /**
     * Check if the user requested to exit the program
     *
     * @return Whether exit has been requested
     */
    public boolean getExitRequested() {
        return exitRequested;
    }
}
