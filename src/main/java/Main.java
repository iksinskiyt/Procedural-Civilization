import GUI.GUI;
import Simulation.DataCollector;
import Terrain.Map;
import Structures.SimulationOptions;

import javax.swing.Timer;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class
 */
public class Main {
    /**
     * GUI object
     */
    private static GUI gui;

    /**
     * Map object
     */
    private static Map map;

    /**
     * Data collector object
     */
    private static DataCollector dataCollector;

    /**
     * Main method. Initializes GUI, map and data collector, gets data from user
     * and starts the simulation timer.
     *
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        gui = new GUI();
        SimulationOptions simulationOptions = gui.getOptionsFromUser();
        map = new Map(simulationOptions);
        gui.openMainWindow(map);

        try {
            dataCollector = new DataCollector(map);
            dataCollector.printHeaderLine();
        }
        catch (FileNotFoundException e) {
            gui.showMessage(
                    "Unable to open the data output file: " + e.getMessage(),
                    true);
        }
        catch (IOException e) {
            gui.showMessage(
                    "Unable to write to the output file: " + e.getMessage(),
                    true);
        }

        Timer simulationTimer =
                new Timer(simulationOptions.simulationSpeed, actionEvent -> {
                    try {
                        dataCollector.collectData();
                    }
                    catch (IOException e) {
                        gui.showMessage("Unable to write to the output file: " +
                                e.getMessage(), true);
                    }
                    if (map.isSimulationComplete()) {
                        gui.showMessage("Simulation complete", false);
                        exit();
                    }
                    if (gui.getExitRequested())
                        exit();
                    map.simulationTick();
                    gui.showSimulation();
                });
        simulationTimer.start();
    }

    /**
     * Close the data output file, destroy the main window and exit the
     * application
     */
    public static void exit() {
        try {
            dataCollector.closeOutputFile();
        }
        catch (IOException e) {
            gui.showMessage(
                    "Unable to close the output file: " + e.getMessage(), true);
        }
        gui.closeMainWindow();
        System.exit(0);
    }
}
