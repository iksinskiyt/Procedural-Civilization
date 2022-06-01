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
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.exit(0);
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
